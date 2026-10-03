import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    private static final int MAX_TRAVELERS = 50;

    private JFrame frame;
    private JSpinner travelerCountSpinner;
    private JPanel travelersPanel;
    private JComboBox<String> destinationBox;
    private JComboBox<String> classBox;
    private JSpinner durationSpinner;
    private JTextArea resultArea;
    private final List<JTextField> nameFields = new ArrayList<>();
    private final List<JSpinner> ageSpinners = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Travel Fare Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setMinimumSize(new Dimension(760, 600));
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(root);

        JLabel title = new JLabel("Travel Fare Calculator", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        root.add(title, BorderLayout.NORTH);

        JPanel left = buildInputPanel();
        JPanel right = buildResultPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setDividerLocation(430);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);
        root.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Trip Details"));

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        travelerCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, MAX_TRAVELERS, 1));
        destinationBox = new JComboBox<>(new String[]{"Mumbai", "Delhi", "Goa", "Bangalore"});
        classBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));

        addFormRow(top, gbc, 0, "Number of travelers:", travelerCountSpinner);
        addFormRow(top, gbc, 1, "Destination:", destinationBox);
        addFormRow(top, gbc, 2, "Travel class:", classBox);
        addFormRow(top, gbc, 3, "Trip duration (days):", durationSpinner);

        panel.add(top, BorderLayout.NORTH);

        travelersPanel = new JPanel();
        travelersPanel.setLayout(new BoxLayout(travelersPanel, BoxLayout.Y_AXIS));
        travelersPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane travelerScroll = new JScrollPane(travelersPanel);
        travelerScroll.setBorder(BorderFactory.createTitledBorder("Traveler Registration"));
        travelerScroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(travelerScroll, BorderLayout.CENTER);

        travelerCountSpinner.addChangeListener(e -> rebuildTravelerFields());
        rebuildTravelerFields();

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearButton = new JButton("Clear");
        JButton calculateButton = new JButton("Calculate Fare");
        calculateButton.setPreferredSize(new Dimension(140, 32));
        clearButton.setPreferredSize(new Dimension(90, 32));

        clearButton.addActionListener(e -> clearForm());
        calculateButton.addActionListener(e -> calculateFare());

        buttons.add(clearButton);
        buttons.add(calculateButton);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Trip Quotation"));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        resultArea.setLineWrap(false);
        resultArea.setText("Your quotation will appear here.\n\nFill in the trip details and click\"Calculate Fare\".");
        resultArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane resultScroll = new JScrollPane(resultArea);
        panel.add(resultScroll, BorderLayout.CENTER);
        return panel;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void rebuildTravelerFields() {
        if (travelersPanel == null || travelerCountSpinner == null) {
            return;
        }

        int count = (Integer) travelerCountSpinner.getValue();
        travelersPanel.removeAll();
        nameFields.clear();
        ageSpinners.clear();

        for (int i = 0; i < count; i++) {
            JPanel row = new JPanel(new GridBagLayout());
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3, 3, 3, 3);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel number = new JLabel("Traveler " + (i + 1));
            number.setPreferredSize(new Dimension(75, 25));

            JTextField nameField = new JTextField();
            nameField.setToolTipText("Enter the traveler's full name");

            JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));

            gbc.gridx = 0;
            gbc.weightx = 0;
            row.add(number, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            row.add(nameField, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0;
            row.add(new JLabel("Age:"), gbc);

            gbc.gridx = 3;
            gbc.weightx = 0;
            ageSpinner.setPreferredSize(new Dimension(65, 25));
            row.add(ageSpinner, gbc);

            nameFields.add(nameField);
            ageSpinners.add(ageSpinner);
            travelersPanel.add(row);
        }

        travelersPanel.add(Box.createVerticalGlue());
        travelersPanel.revalidate();
        travelersPanel.repaint();
    }

    private void calculateFare() {
        int numberOfTravelers = (Integer) travelerCountSpinner.getValue();

        Traveler[] travelers = new Traveler[numberOfTravelers];
        for (int i = 0; i < numberOfTravelers; i++) {
            String name = nameFields.get(i).getText().trim();
            if (name.isEmpty()) {
                showError("Please enter a name for Traveler " + (i + 1) + ".", nameFields.get(i));
                return;
            }

            int age = (Integer) ageSpinners.get(i).getValue();
            if (age <= 0) {
                showError("Please enter a valid age for Traveler " + (i + 1) + ".", ageSpinners.get(i));
                return;
            }

            travelers[i] = new Traveler(name, age);
        }

        String destination = (String) destinationBox.getSelectedItem();
        String travelClass = (String) classBox.getSelectedItem();
        int duration = (Integer) durationSpinner.getValue();

        if (duration <= 0) {
            showError("Trip duration must be greater than 0 days.", durationSpinner);
            return;
        }

        double baseFare = Destination.getBaseFare(destination);
        double classMultiplier = TravelClass.getClassMultiplier(travelClass);
        double totalFare = FareCalculator.calculateBasicFare(
                baseFare, classMultiplier, duration, numberOfTravelers);
        double discount = FareCalculator.calculateDiscount(totalFare, numberOfTravelers);
        double finalFare = FareCalculator.calculateFinalFare(totalFare, discount);
        double discountRate = FareCalculator.getDiscountRate(numberOfTravelers);

        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN"));
        StringBuilder result = new StringBuilder();
        result.append("==========================================\n");
        result.append("              TRIP QUOTATION\n");
        result.append("==========================================\n\n");
        result.append(String.format("Destination        : %s%n", destination));
        result.append(String.format("Travel Class       : %s%n", travelClass));
        result.append(String.format("Duration           : %d days%n", duration));
        result.append(String.format("Number of Travelers: %d%n%n", numberOfTravelers));

        result.append("----- Traveler Details -----\n");
        for (int i = 0; i < numberOfTravelers; i++) {
            result.append(String.format("%d. %s - Age: %d%n",
                    i + 1, travelers[i].getName(), travelers[i].getAge()));
        }

        result.append("\n----- Fare Details -----\n");
        result.append(String.format("Base Fare per Traveler/Day : %s%n", currency.format(baseFare)));
        result.append(String.format("Class Multiplier            : %.1f%n", classMultiplier));
        result.append(String.format("Total Fare Before Discount  : %s%n", currency.format(totalFare)));
        result.append(String.format("Group Discount              : %.0f%%%n", discountRate));
        result.append(String.format("Discount Amount             : %s%n", currency.format(discount)));
        result.append(String.format("Final Travel Cost           : %s%n", currency.format(finalFare)));
        result.append("==========================================\n");

        resultArea.setText(result.toString());
        resultArea.setCaretPosition(0);
    }

    private void showError(String message, Component focusComponent) {
        JOptionPane.showMessageDialog(frame, message, "Invalid Input", JOptionPane.WARNING_MESSAGE);
        focusComponent.requestFocusInWindow();
    }

    private void clearForm() {
        travelerCountSpinner.setValue(1);
        destinationBox.setSelectedIndex(0);
        classBox.setSelectedIndex(0);
        durationSpinner.setValue(1);
        if (!nameFields.isEmpty()) {
            nameFields.get(0).setText("");
        }
        resultArea.setText("Your quotation will appear here.\n\nFill in the trip details and click\"Calculate Fare\".");
        resultArea.setCaretPosition(0);
    }
}
