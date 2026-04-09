import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TudenxLabGUI extends JFrame {
    private DefaultListModel<String> patientListModel = new DefaultListModel<>();
    private JList<String> patientList = new JList<>(patientListModel);
    private Map<String, P_PATIENTniRey> patients = new HashMap<>();
    private java.util.List<Object[]> testControls = new ArrayList<>();
    private JTextField nameField;
    private JTextField ageField;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JCheckBox pregnancyCheckBox;


    public TudenxLabGUI() {


        setTitle("TudenXLab");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(50,50));


        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(120, 25));

        ageField = new JTextField();
        ageField.setPreferredSize(new Dimension(60, 25));

        maleCheckBox = new JCheckBox("M");
        femaleCheckBox = new JCheckBox("F");
        pregnancyCheckBox = new JCheckBox("Pregnant");


        JPanel patientPanel = new JPanel(new BorderLayout());
        JLabel patientLabel = new JLabel("=== CURRENT PATIENTS ===", JLabel.CENTER);
        patientLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientPanel.add(patientLabel, BorderLayout.NORTH);
        patientList.setFont(new Font("Arial", Font.PLAIN, 16));
        patientPanel.add(new JScrollPane(patientList), BorderLayout.CENTER);

        patientList.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            String selected = patientList.getSelectedValue();
            if (selected != null && selected.contains("|")) {
                String id = selected.split("\\|")[0].trim();
                P_PATIENTniRey p = patients.get(id);

                if (p != null && pregnancyCheckBox != null) {
                    JTextField pregField = null;
                    for (Object[] row : testControls) {
                        if (row[2].equals("Pregnancy Test")) {
                            pregField = (JTextField) row[1];
                            break;
                        }
                    }

                    if (p.GETGENDER().equalsIgnoreCase("M")) {
                        pregnancyCheckBox.setEnabled(false);
                        if (pregField != null) pregField.setEnabled(false);
                    } else {
                        pregnancyCheckBox.setEnabled(true);
                        if (pregField != null) pregField.setEnabled(true);
                    }
                }
            }
        }
    });

        // === Right: Add Patient ===
        JPanel inputPanel = new JPanel(new GridLayout(5,2,10,10));
        JLabel addLabel = new JLabel("ADD PATIENTS HERE!", JLabel.CENTER);
        addLabel.setFont(new Font("Arial", Font.BOLD, 20));
        inputPanel.add(addLabel);
        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel("Patient's Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Patient's Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Patient's Gender:"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleCheckBox);
        genderPanel.add(femaleCheckBox);
        inputPanel.add(genderPanel);

        // Ensure only one can be selected
        maleCheckBox.addActionListener(e -> {
            if (maleCheckBox.isSelected()) femaleCheckBox.setSelected(false);
        });
        femaleCheckBox.addActionListener(e -> {
            if (femaleCheckBox.isSelected()) maleCheckBox.setSelected(false);
        });
        JButton addBtn = new JButton("ADD PATIENT");
        inputPanel.add(addBtn);

        // === Center: Laboratory Menu ===
        JPanel testPanel = new JPanel(new GridLayout(0,1,5,5));
        JLabel labLabel = new JLabel("=== LABORATORY MENU ===", JLabel.CENTER);
        labLabel.setFont(new Font("Arial", Font.BOLD, 20));
        testPanel.add(labLabel);

        java.util.function.BiConsumer<String,String> addRow = (testName, unit) -> {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JCheckBox cb = new JCheckBox(testName);
            JTextField tf = new JTextField(8);
            row.add(cb);
            row.add(new JLabel(unit));
            row.add(tf);
            testPanel.add(row);
            testControls.add(new Object[]{cb, tf, testName, unit});
            if(testName.equals("Pregnancy Test")) pregnancyCheckBox = cb;

            cb.addActionListener(e -> {
                if(patientList.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(this, "Select a patient first!");
                    cb.setSelected(false);
                }
            });
        };

        testPanel.add(new JLabel("CLINICAL CHEMISTRY LAB", JLabel.LEFT));
        addRow.accept("FBS Test", "mg/dL");
        addRow.accept("RBS Test", "mg/dL");
        addRow.accept("Total Cholesterol", "mg/dL");
        addRow.accept("HDL Test", "mg/dL");
        addRow.accept("LDL Test", "mg/dL");
        addRow.accept("Triglycerides Test", "mg/dL");
        addRow.accept("Creatinine Test", "mg/dL");
        addRow.accept("Uric Acid Test", "mg/dL");
        addRow.accept("BUN Test", "mg/dL");
        addRow.accept("AST Test", "U/L");
        addRow.accept("ALT Test", "U/L");
        addRow.accept("Sodium Test", "mmol/L");
        addRow.accept("Potassium Test", "mmol/L");
        addRow.accept("Chloride Test", "mmol/L");
        addRow.accept("Total Calcium Test", "mg/dL");
        addRow.accept("Ionized Calcium Test", "mmol/L");

        testPanel.add(new JLabel("MOLECULAR / PCR LAB", JLabel.LEFT));
        addRow.accept("COVID-19 RT-PCR", "Ct");
        addRow.accept("HIV Viral Load", "copies/mL");
        addRow.accept("Hepatitis B DNA", "IU/mL");
        addRow.accept("HPV DNA", "IU/mL");

        testPanel.add(new JLabel("MICROBIOLOGY LAB", JLabel.LEFT));
        addRow.accept("Urine Culture", "CFU/mL");
        addRow.accept("Sputum Culture", "CFU/mL");
        addRow.accept("Throat Swab Culture", "CFU/mL");
        addRow.accept("Wound Culture", "CFU/mL");

        testPanel.add(new JLabel("CLINICAL MICROSCOPY LAB", JLabel.LEFT));
        addRow.accept("Urinalysis - Protein", "mg/dL");
        addRow.accept("Fat Test", "%");
        addRow.accept("Pregnancy Test", "mIU/mL");
        addRow.accept("Urinalysis - Blood", "RBC/uL");

        JScrollPane scrollPane = new JScrollPane(testPanel);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 20));
        JButton removeBtn = new JButton("REMOVE PATIENT");
        JButton resultBtn = new JButton("PROCESS RESULT");
        JButton billingBtn = new JButton("BILLING");
        JButton exitBtn = new JButton("EXIT");
        buttonPanel.add(removeBtn);
        buttonPanel.add(resultBtn);
        buttonPanel.add(billingBtn);
        buttonPanel.add(exitBtn);

        add(patientPanel, BorderLayout.WEST);
        add(inputPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // === Actions ===
        addBtn.addActionListener(e -> addPatient());
        removeBtn.addActionListener(e -> removePatient());
        resultBtn.addActionListener(e -> processResults());
        billingBtn.addActionListener(e -> showBilling());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void addPatient() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = maleCheckBox.isSelected() ? "M" : (femaleCheckBox.isSelected() ? "F" : "");

        if(name.isEmpty() || ageText.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        int age = Integer.parseInt(ageText);
        P_PATIENTniRey p = new P_PATIENTniRey(name, age, gender);
        patients.put(p.GETPATIENTID(), p);
        patientListModel.addElement(p.GETPATIENTID() + " | " + name + " | " + gender);

        nameField.setText("");
        ageField.setText("");
        gender = maleCheckBox.isSelected() ? "M" : (femaleCheckBox.isSelected() ? "F" : "");
    }

    private void removePatient() {
        String patientId = JOptionPane.showInputDialog(this, "Enter Patient ID to Remove:");
        if(patientId == null || patientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID is required.");
            return;
        }
        P_PATIENTniRey p = patients.remove(patientId.trim());
        if(p != null) {
            patientListModel.removeElement(patientId.trim() + " | " + p.GETNAME() + " | " + p.GETGENDER());
        } else {
            JOptionPane.showMessageDialog(this, "NO PATIENT FOUND: " + patientId);
        }
    }

    private void resetInputsAndTests() {
        // Clear patient input fields
        nameField.setText("");
        ageField.setText("");
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);

        // Reset all test checkboxes and text fields
        for(Object[] row : testControls) {
            JCheckBox cb = (JCheckBox) row[0];
            JTextField tf = (JTextField) row[1];
            cb.setSelected(false);
            tf.setText("");
        }
    }

        // Attach selected tests to patient
    private void assignTestsToPatient(P_PATIENTniRey p) {
        for(Object[] row : testControls) {
            JCheckBox cb = (JCheckBox) row[0];
            JTextField tf = (JTextField) row[1];
            String testName = (String) row[2];
            String unit = (String) row[3];

            if(cb.isSelected() && !tf.getText().trim().isEmpty()) {
                try {
                    double value = Double.parseDouble(tf.getText().trim());
                    P_LABTESTniRey test;

                    // === Clinical Chemistry ===
                    if(testName.equalsIgnoreCase("FBS Test") ||
                    testName.equalsIgnoreCase("RBS Test") ||
                    testName.equalsIgnoreCase("Total Cholesterol") ||
                    testName.equalsIgnoreCase("HDL Test") ||
                    testName.equalsIgnoreCase("LDL Test") ||
                    testName.equalsIgnoreCase("Triglycerides Test") ||
                    testName.equalsIgnoreCase("Creatinine Test") ||
                    testName.equalsIgnoreCase("Uric Acid Test") ||
                    testName.equalsIgnoreCase("BUN Test") ||
                    testName.equalsIgnoreCase("AST Test") ||
                    testName.equalsIgnoreCase("ALT Test") ||
                    testName.equalsIgnoreCase("Sodium Test") ||
                    testName.equalsIgnoreCase("Potassium Test") ||
                    testName.equalsIgnoreCase("Chloride Test") ||
                    testName.equalsIgnoreCase("Total Calcium Test") ||
                    testName.equalsIgnoreCase("Ionized Calcium Test")) {
                        test = new S_CLINICALCHEMISTRYniRey(testName, value, unit, p.GETGENDER());

                    // === Molecular / PCR ===
                    } else if(testName.equalsIgnoreCase("COVID-19 RT-PCR") ||
                            testName.equalsIgnoreCase("HIV Viral Load") ||
                            testName.equalsIgnoreCase("Hepatitis B DNA") ||
                            testName.equalsIgnoreCase("HPV DNA")) {
                        test = new S_MOLECULARPCRniRey(testName, value, unit);

                    // === Microbiology ===
                    } else if(testName.equalsIgnoreCase("Urine Culture") ||
                            testName.equalsIgnoreCase("Sputum Culture") ||
                            testName.equalsIgnoreCase("Throat Swab Culture") ||
                            testName.equalsIgnoreCase("Wound Culture")) {
                        test = new S_MICROBIOLOGYniRey(testName, value, unit);

                    // === Clinical Microscopy ===
                    } else if(testName.equalsIgnoreCase("Urinalysis - Protein") ||
                            testName.equalsIgnoreCase("Fat Test") ||
                            testName.equalsIgnoreCase("Pregnancy Test") ||
                            testName.equalsIgnoreCase("Urinalysis - Blood")) {
                        test = new S_CLINICALMICROSCOPYniRey(testName, value, unit);

                    } else {
                        // fallback
                        test = new S_CLINICALMICROSCOPYniRey(testName, value, unit);
                    }

                    // === NEW LOGIC: override if test already exists ===
                    boolean replaced = false;
                    for (int i = 0; i < p.GETTESTS().size(); i++) {
                        P_LABTESTniRey existing = p.GETTESTS().get(i);
                        if (existing.TestName.equalsIgnoreCase(testName)) {
                            p.GETTESTS().set(i, test); // replace
                            replaced = true;
                            break;
                        }
                    }
                    if (!replaced) {
                        p.ADDTEST(test); // add new
                    }

                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid value for " + testName);
                }
            }
        }
    }




    private void processResults() {
        String patientId = JOptionPane.showInputDialog(this, "Enter Patient ID for Results:");
        if(patientId == null || patientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID is required.");
            return;
        }
        P_PATIENTniRey p = patients.get(patientId.trim());
        if(p == null) {
            JOptionPane.showMessageDialog(this, "No patient found with ID: " + patientId);
            return;
        }

        // Save selected tests to patient before showing results
        assignTestsToPatient(p);

        Map<String, java.util.List<P_LABTESTniRey>> groupedTests = new LinkedHashMap<>();
        LocalDateTime resultAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

        for(P_LABTESTniRey test : p.GETTESTS()) {
            String category;
            if(test instanceof S_CLINICALMICROSCOPYniRey) category = "MICROSCOPY";
            else if(test instanceof S_MICROBIOLOGYniRey) category = "MICROBIOLOGY";
            else if(test instanceof S_MOLECULARPCRniRey) category = "MOLECULAR / PCR";
            else if(test instanceof S_CLINICALCHEMISTRYniRey) category = "CLINICAL CHEMISTRY";
            else category = "OTHER";

            groupedTests.putIfAbsent(category, new ArrayList<>());
            groupedTests.get(category).add(test);
        }

        JFrame resultFrame = new JFrame("Patient Results");
        resultFrame.setSize(1200, 800);
        resultFrame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        String[] columns = {"Test Name","Result","Value","Unit","Taken","Result Ready","Cost"};

        for(Map.Entry<String, java.util.List<P_LABTESTniRey>> entry : groupedTests.entrySet()) {
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for(P_LABTESTniRey test : entry.getValue()) {
                model.addRow(new Object[]{
                    test.TestName,
                    test.EVALUATERESULT(),
                    test.InputValue,
                    test.Unit,
                    test.GETTAKENAT().format(formatter),
                    resultAt.format(formatter),
                    String.format("PhP %.2f", test.GETCOST())
                });
            }
            JTable table = new JTable(model);
            table.setFont(new Font("Monospaced", Font.PLAIN, 14));
            table.setRowHeight(24);
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            tabbedPane.addTab(entry.getKey(), new JScrollPane(table));
        }

        resultFrame.add(tabbedPane, BorderLayout.CENTER);
        resultFrame.setLocationRelativeTo(this);
        resultFrame.setVisible(true);
        resetInputsAndTests();
    }

    private void showBilling() {
        String patientId = JOptionPane.showInputDialog(this, "Enter Patient ID for Billing:");
        if(patientId == null || patientId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID is required.");
            return;
        }

        P_PATIENTniRey p = patients.get(patientId.trim());
        if(p == null) {
            JOptionPane.showMessageDialog(this, "No patient found with ID: " + patientId);
            return;
        }

        // Save selected tests to patient before billing
        assignTestsToPatient(p);

        Map<String, java.util.List<P_LABTESTniRey>> groupedTests = new LinkedHashMap<>();
        double totalCost = 0.0;
        LocalDateTime resultAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

        for(P_LABTESTniRey test : p.GETTESTS()) {
            String category;
            if(test instanceof S_CLINICALMICROSCOPYniRey) category = "MICROSCOPY";
            else if(test instanceof S_MICROBIOLOGYniRey) category = "MICROBIOLOGY";
            else if(test instanceof S_MOLECULARPCRniRey) category = "MOLECULAR / PCR";
            else if(test instanceof S_CLINICALCHEMISTRYniRey) category = "CLINICAL CHEMISTRY";
            else category = "OTHER";

            groupedTests.putIfAbsent(category, new ArrayList<>());
            groupedTests.get(category).add(test);
            totalCost += test.GETCOST();
        }

        StringBuilder receiptText = new StringBuilder();
        receiptText.append("============================================\n");
        receiptText.append("              TudenXLab Receipt              \n");
        receiptText.append("============================================\n");
        receiptText.append("Patient: ").append(p.GETNAME())
                   .append(" (ID: ").append(p.GETPATIENTID()).append(")\n\n");

        for(Map.Entry<String, java.util.List<P_LABTESTniRey>> entry : groupedTests.entrySet()) {
            double categoryTotal = entry.getValue().stream().mapToDouble(P_LABTESTniRey::GETCOST).sum();
            receiptText.append("== ").append(entry.getKey()).append(" ==\n");
            for(P_LABTESTniRey test : entry.getValue()) {
                receiptText.append("   - ").append(test.TestName)
                           .append(" : PhP ").append(String.format("%.2f", test.GETCOST()))
                           .append("\n");
            }
            receiptText.append("Subtotal: PhP ").append(String.format("%.2f", categoryTotal)).append("\n\n");
        }

        receiptText.append("--------------------------------------------\n");
        receiptText.append("GRAND TOTAL: PhP ").append(String.format("%.2f", totalCost)).append("\n");
        receiptText.append("Date: ").append(resultAt.format(formatter)).append("\n");
        receiptText.append("============================================");

        JTextArea receiptArea = new JTextArea(receiptText.toString());
        receiptArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        receiptArea.setEditable(false);

        JFrame receiptFrame = new JFrame("Billing Receipt");
        receiptFrame.setSize(800, 600);
        receiptFrame.setLayout(new BorderLayout());
        receiptFrame.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        receiptFrame.setLocationRelativeTo(this);
        receiptFrame.setVisible(true);
        resetInputsAndTests();
    }

    // === MAIN ENTRY POINT ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TudenxLabGUI().setVisible(true);
        });
    }
}