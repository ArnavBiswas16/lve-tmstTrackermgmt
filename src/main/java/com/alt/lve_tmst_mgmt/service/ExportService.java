package com.alt.lve_tmst_mgmt.service;

import com.alt.lve_tmst_mgmt.dto.FinancialTrackerReportDto;
import com.alt.lve_tmst_mgmt.dto.MonthlyEmployeeReportDto;
import com.alt.lve_tmst_mgmt.repository.ReportRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExportService {

    @Autowired
    private ReportRepo reportRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseEntity<byte[]> exportEmployeeReportsToCSV(
            String sowId,
            LocalDate monthStart,
            LocalDate monthEnd) {

        try {

            List<MonthlyEmployeeReportDto> reports =
                    reportRepo.exportCompleteMonthlySOWReport(sowId, monthStart, monthEnd);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                 CSVPrinter csvPrinter = new CSVPrinter(writer,
                         CSVFormat.DEFAULT.withHeader(generateHeaders(monthStart)))) {

                for (MonthlyEmployeeReportDto report : reports) {

                    Map<LocalDate, String> dayStatusMap =
                            buildDayStatusMap(report);

                    List<Object> row = new ArrayList<>();

                    // Static Columns
                    row.add(report.getEmployeeId());
                    row.add(report.getName());
                    row.add(report.getLocation());
                    row.add(report.getSowId());
                    row.add(report.getAssignmentStartDate());
                    row.add(report.getNumberOfHalfDays());
                    row.add(report.getNumberOfLeaves());
                    row.add(report.getNumberOfHolidays());


                    // Daily Columns
                    int totalDays = monthStart.lengthOfMonth();

                    for (int day = 1; day <= totalDays; day++) {
                        LocalDate currentDate = monthStart.withDayOfMonth(day);
                        String value = dayStatusMap.getOrDefault(currentDate, "X");
                        row.add(value);
                    }


                    // Weekly Hours FROM DATABASE

                    int[] weekTotals = extractWeeklyHours(report);

                    for (int w : weekTotals) {
                        row.add(w);
                    }

                    // Compliance Flags
                    row.add(Boolean.TRUE.equals(report.getPtsSaved()) ? "YES" : "NO");
                    row.add(Boolean.TRUE.equals(report.getCofyUpdate()) ? "YES" : "NO");
                    row.add(Boolean.TRUE.equals(report.getCitiTraining()) ? "YES" : "NO");

                    csvPrinter.printRecord(row);
                }
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=employee_reports.csv")
                    .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                    .body(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV file", e);
        }
    }

    // ======================================================
    // Extract Weekly Hours (ONLY from DB JSON)
    // ======================================================

    private int[] extractWeeklyHours(MonthlyEmployeeReportDto report) throws Exception {

        int[] weekTotals = new int[5]; // W1–W5 default 0

        if (report.getWeeklyHours() == null || report.getWeeklyHours().isEmpty()) {
            return weekTotals;
        }

        JsonNode weeklyArray = objectMapper.readTree(report.getWeeklyHours());

        int index = 0;

        for (JsonNode node : weeklyArray) {

            if (index >= 5) break;

            int hours = node.has("hours") && !node.get("hours").isNull()
                    ? node.get("hours").asInt()
                    : 0;

            weekTotals[index] = hours;

            index++;
        }

        return weekTotals;
    }

    // ======================================================
    // Build Daily Status Map
    // ======================================================

    private Map<LocalDate, String> buildDayStatusMap(
            MonthlyEmployeeReportDto report) throws Exception {

        Map<LocalDate, String> map = new HashMap<>();

        // Holidays
        if (report.getHolidays() != null) {
            JsonNode holidays = objectMapper.readTree(report.getHolidays());
            for (JsonNode node : holidays) {
                LocalDate date = LocalDate.parse(node.get("date").asText());
                map.put(date, "H");
            }
        }

        // Leaves
        if (report.getLeaves() != null) {
            JsonNode leaves = objectMapper.readTree(report.getLeaves());
            for (JsonNode node : leaves) {
                LocalDate date = LocalDate.parse(node.get("startDate").asText());
                map.put(date, "L");
            }
        }

        // Timesheets
        if (report.getTimesheets() != null) {
            JsonNode timesheets = objectMapper.readTree(report.getTimesheets());
            for (JsonNode node : timesheets) {

                LocalDate date = LocalDate.parse(node.get("workDate").asText());
                int hours = node.get("hoursLogged").asInt();

                if (hours <= 4) {
                    map.put(date, "HD");
                } else {
                    map.put(date, String.valueOf(hours));
                }
            }
        }

        return map;
    }

    public ResponseEntity<byte[]> exportMonthlyFinancialTracker(
            String sowId,
            LocalDate monthStart,
            LocalDate monthEnd) {

        try {
            List<FinancialTrackerReportDto> reports = new ArrayList<>();

            if(sowId !=null) {
                 reports = reportRepo.fetchMonthlyFinancialTrackerBySOW(sowId, monthStart, monthEnd);
            } else {
                 reports = reportRepo.fetchMonthlyFinancialTracker(monthStart, monthEnd);
            }
            if (reports == null) {
                reports = new ArrayList<>();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                 CSVPrinter csvPrinter = new CSVPrinter(writer,
                         CSVFormat.DEFAULT.withHeader(generateSowSummaryHeaders()))) {

                System.out.println(reports);
                for (FinancialTrackerReportDto report : reports) {

                    List<Object> row = new ArrayList<>();

                    row.add(report.getSowName());
                    row.add(report.getSowId());
                    row.add(report.getSoeId());
                    row.add(report.getEmployeeId());
                    row.add(report.getName());
                    row.add(report.getPtsTotalMonthlyHours());
                    row.add(report.getTotalLeaves());

                    csvPrinter.printRecord(row);
                }
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=sow_monthly_summary.csv")
                    .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                    .body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating SOW monthly summary CSV", e);
        }
    }

    private String[] generateSowSummaryHeaders() {

        return new String[]{
                "SOW Name",
                "SOW ID",
                "SOE ID",
                "Employee ID",
                "Name",
                "PTS Total Monthly Hours",
                "Total Leaves"
        };
    }

    // ======================================================
    // Generate CSV Headers
    // ======================================================

    private String[] generateHeaders(LocalDate monthStart) {

        List<String> headers = new ArrayList<>();

        headers.add("Employee ID");
        headers.add("Name");
        headers.add("Location");
        headers.add("SOW ID");
        headers.add("Assignment Start Date");
        headers.add("Half Days");
        headers.add("Leaves");
        headers.add("Holidays");

        int totalDays = monthStart.lengthOfMonth();
        for (int i = 1; i <= totalDays; i++) {
            headers.add(String.valueOf(i));
        }

        headers.addAll(Arrays.asList(
                "W1", "W2", "W3", "W4", "W5",
                "PTS Saved", "COFY Update", "CITI Training"
        ));

        return headers.toArray(new String[0]);
    }
}