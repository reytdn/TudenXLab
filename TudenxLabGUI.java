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
    private JCheckBox pwdCheckBox;

    public TudenxLabGUI() {
        setTitle("TudenXLab");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(50,50));

        add(buildHeader(), BorderLayout.NORTH);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(200, 25));

        ageField = new JTextField();
        ageField.setMaximumSize(new Dimension(100, 25));

        maleCheckBox = new JCheckBox("M");
        femaleCheckBox = new JCheckBox("F");

        // === Patient List Panel ===
        JPanel patientPanel = new JPanel(new BorderLayout());
        patientPanel.setBackground(new Color(200, 230, 255));
        JLabel patientLabel = new JLabel("=== CURRENT PATIENTS ===", JLabel.CENTER);
        patientLabel.setFont(new Font("Arial Black", Font.BOLD, 22));
        patientLabel.setForeground(Color.RED);
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

        // === Add Patients Panel (stacked form style) ===
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(200, 230, 255));

        JLabel addLabel = new JLabel("ADD PATIENTS HERE!", JLabel.CENTER);
        addLabel.setFont(new Font("Arial Black", Font.BOLD, 22));
        addLabel.setForeground(Color.RED);
        addLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(addLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel nameLabel = new JLabel("Patient's Name:");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(nameLabel);

        inputPanel.add(nameField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel ageLabel = new JLabel("Patient's Age:");
        ageLabel.setForeground(Color.BLACK);
        ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(ageLabel);

        inputPanel.add(ageField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel genderLabel = new JLabel("Patient's Gender (M/F):");
        genderLabel.setForeground(Color.BLACK);
        genderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(genderLabel);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        genderPanel.setBackground(new Color(200, 230, 255));
        maleCheckBox.setForeground(Color.BLACK);
        femaleCheckBox.setForeground(Color.BLACK);
        genderPanel.add(maleCheckBox);
        genderPanel.add(femaleCheckBox);
        inputPanel.add(genderPanel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        maleCheckBox.addActionListener(e -> {
            if (maleCheckBox.isSelected()) femaleCheckBox.setSelected(false);
        });
        femaleCheckBox.addActionListener(e -> {
            if (femaleCheckBox.isSelected()) maleCheckBox.setSelected(false);
        });

        JPanel pwdPanel = new JPanel();
        pwdPanel.setLayout(new BoxLayout(pwdPanel, BoxLayout.Y_AXIS));
        pwdPanel.setBackground(new Color(200, 230, 255));

        JLabel pwdLabel = new JLabel("Check if PWD:");
        pwdLabel.setForeground(Color.BLACK);
        pwdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwdCheckBox = new JCheckBox("PWD");
        pwdCheckBox.setForeground(Color.BLACK);
        pwdCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwdPanel.add(pwdLabel);
        pwdPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        pwdPanel.add(pwdCheckBox);

        inputPanel.add(pwdPanel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton addBtn = new JButton("ADD PATIENT");
        addBtn.setBackground(new Color(200, 230, 255));
        addBtn.setForeground(new Color(34, 139, 34));
        addBtn.setFont(new Font("Arial Black", Font.BOLD, 20));
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(addBtn);

        addBtn.addActionListener(e -> addPatient());

        // === Laboratory Menu Panel ===
        JPanel testPanel = new JPanel(new GridLayout(0,1,5,5));
        testPanel.setBackground(new Color(200, 230, 255));
        JLabel labLabel = new JLabel("=== LABORATORY MENU ===", JLabel.CENTER);
        labLabel.setFont(new Font("Arial Black", Font.BOLD, 22));
        labLabel.setForeground(Color.RED);
        testPanel.add(labLabel);

        java.util.function.BiConsumer<String,String> addRow = (testName, unit) -> {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setBackground(new Color(200, 230, 255));
            JCheckBox cb = new JCheckBox(testName);
            cb.setForeground(Color.BLACK);
            JTextField tf = new JTextField(6); // smaller input box
            row.add(cb);
            row.add(new JLabel(unit));
            row.add(tf);
            testPanel.add(row);
            testControls.add(new Object[]{cb, tf, testName, unit});
            if(testName.equals("Pregnancy Test")) pregnancyCheckBox = cb;

            cb.addActionListener(e -> {
            if (patientList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(this, "Select a patient first!");
                cb.setSelected(false);
                tf.setEnabled(false);
                tf.setText("");
                return;
            }

            if (cb.isSelected()) {
                tf.setEnabled(true);
            } else {
                tf.setEnabled(false);
                tf.setText("");
            }
        });

        // Default state: disabled until checkbox is ticked AND patient is selected
        tf.setEnabled(false);
        }; // <-- CLOSE the addRow lambda here
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

        // Tabs for Lab Sections
        JTabbedPane labTabs = buildTabs(testPanel);
        JScrollPane scrollPane = new JScrollPane(labTabs);

        // Status Bar
        JPanel buttonPanel = buildStatusBar();

        add(patientPanel, BorderLayout.WEST);
        add(inputPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setupMutualExclusions();
    }

    // === Updated Header Method ===
    private JPanel buildHeader() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel headerLabel = new JLabel(
            "<html><span style='color:blue'>Tuden</span><span style='color:green'>X</span><span style='color:red'>Lab</span></html>",
            JLabel.CENTER
        );
        // Arial Black font, bold, large size
        headerLabel.setFont(new Font("Arial Black", Font.BOLD, 64));
        headerPanel.add(headerLabel);
        return headerPanel;
    }
    private JTabbedPane buildTabs(JPanel testPanel) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));
        tabbedPane.addTab("Laboratory Tests", new JScrollPane(testPanel));
        return tabbedPane;
    }

    private JPanel buildStatusBar() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton removeBtn = new JButton("REMOVE PATIENT");
        JButton resultBtn = new JButton("PROCESS RESULT");
        JButton billingBtn = new JButton("BILLING");
        JButton exitBtn = new JButton("EXIT");

        removeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        resultBtn.setFont(new Font("Arial", Font.BOLD, 14));
        billingBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(removeBtn);
        buttonPanel.add(resultBtn);
        buttonPanel.add(billingBtn);
        buttonPanel.add(exitBtn);

        removeBtn.addActionListener(e -> removePatient());
        resultBtn.addActionListener(e -> processResults());
        billingBtn.addActionListener(e -> showBilling());
        exitBtn.addActionListener(e -> System.exit(0));

        return buttonPanel;
    }

    private void addPatient() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = maleCheckBox.isSelected() ? "M" : (femaleCheckBox.isSelected() ? "F" : "");
        boolean isPWD = pwdCheckBox.isSelected(); // ✅ capture checkbox state

        if(name.isEmpty() || ageText.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        int age = Integer.parseInt(ageText);
        // ✅ Pass PWD flag into constructor
        P_PATIENTniRey p = new P_PATIENTniRey(name, age, gender, isPWD);

        patients.put(p.GETPATIENTID(), p);
        patientListModel.addElement(
            p.GETPATIENTID() + " | " + name + " | " + gender + (isPWD ? " | PWD" : "")
        );

        nameField.setText("");
        ageField.setText("");
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);
        pwdCheckBox.setSelected(false);
    }

    private void setupMutualExclusions() {
        addMutualExclusion("FBS Test", "RBS Test");
        addMutualExclusion("RBS Test", "FBS Test");

        // Urine Culture vs Urinalysis group
        addMutualExclusion("Urine Culture", "Urinalysis - Protein");
        addMutualExclusion("Urine Culture", "Urinalysis - Blood");
        addMutualExclusion("Urine Culture", "Pregnancy Test");

        addMutualExclusion("Pregnancy Test", "Urine Culture");
        addMutualExclusion("Pregnancy Test", "Urinalysis - Protein");
        addMutualExclusion("Pregnancy Test", "Urinalysis - Blood");

        addMutualExclusion("Urinalysis - Blood", "Pregnancy Test");
        addMutualExclusion("Urinalysis - Blood", "Urine Culture");
        addMutualExclusion("Urinalysis - Blood", "Urinalysis - Protein");

        addMutualExclusion("Urinalysis - Protein", "Urinalysis - Blood");
        addMutualExclusion("Urinalysis - Protein", "Pregnancy Test");
        addMutualExclusion("Urinalysis - Protein", "Urine Culture");

    }
    // === Helper Method ===
    private void addMutualExclusion(String testA, String testB) {
        for (Object[] row : testControls) {
            if (row[2].equals(testA)) {
                JCheckBox cbA = (JCheckBox) row[0];
                cbA.addActionListener(e -> {
                    for (Object[] row2 : testControls) {
                        if (row2[2].equals(testB)) {
                            JCheckBox cbB = (JCheckBox) row2[0];
                            JTextField tfB = (JTextField) row2[1];
                            if (cbA.isSelected()) {
                                cbB.setEnabled(false);
                                tfB.setEnabled(false);
                                tfB.setText("");
                            } else {
                                cbB.setEnabled(true);
                            }
                        }
                    }
                });
            }
        }
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
            JOptionPane.showMessageDialog(this, "NO PATIENT FOUND WITH ID: " + patientId);
        }
    }

    private void resetInputsAndTests() {
        nameField.setText("");
        ageField.setText("");
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);

        for(Object[] row : testControls) {
            JCheckBox cb = (JCheckBox) row[0];
            JTextField tf = (JTextField) row[1];
            cb.setSelected(false);
            tf.setText("");
        }
    }

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

                    } else if(testName.equalsIgnoreCase("COVID-19 RT-PCR") ||
                            testName.equalsIgnoreCase("HIV Viral Load") ||
                            testName.equalsIgnoreCase("Hepatitis B DNA") ||
                            testName.equalsIgnoreCase("HPV DNA")) {
                        test = new S_MOLECULARPCRniRey(testName, value, unit);


                    } else if(testName.equalsIgnoreCase("Urine Culture") ||
                            testName.equalsIgnoreCase("Sputum Culture") ||
                            testName.equalsIgnoreCase("Throat Swab Culture") ||
                            testName.equalsIgnoreCase("Wound Culture")) {
                        test = new S_MICROBIOLOGYniRey(testName, value, unit);

                    } else if(testName.equalsIgnoreCase("Urinalysis - Protein") ||
                            testName.equalsIgnoreCase("Fat Test") ||
                            testName.equalsIgnoreCase("Pregnancy Test") ||
                            testName.equalsIgnoreCase("Urinalysis - Blood")) {
                        test = new S_CLINICALMICROSCOPYniRey(testName, value, unit);

                    } else {
                        test = new S_CLINICALMICROSCOPYniRey(testName, value, unit);
                    }

                    boolean replaced = false;
                    for (int i = 0; i < p.GETTESTS().size(); i++) {
                        P_LABTESTniRey existing = p.GETTESTS().get(i);
                        if (existing.TestName.equalsIgnoreCase(testName)) {
                            p.GETTESTS().set(i, test); 
                            replaced = true;
                            break;
                        }
                    }
                    if (!replaced) {
                        p.ADDTEST(test); 
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

        receiptText.append("RAW TOTAL: " + totalCost + "\n");

        // ✅ Apply PWD discount if applicable
        if (p.isPWD()) {
            double discount = totalCost * 0.20; // 20% discount
            totalCost -= discount;
            receiptText.append("PWD Discount (20%): -PhP ").append(String.format("%.2f", discount)).append("\n");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TudenxLabGUI().setVisible(true);
        });
    }
}