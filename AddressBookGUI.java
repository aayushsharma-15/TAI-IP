import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddressBookGUI extends JFrame {
    private JTextField nameField, addressField, phoneField, emailField;
    private JButton addButton, updateButton, deleteButton, searchButton, clearButton, exitButton;
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private ArrayList<Contact> contacts;

    public AddressBookGUI() {
        setTitle("Address Book");
        setSize(800, 600); // Increased width and height for better spacing and layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        // Initialize the layout components
        initializeComponents();

        // Create and customize the layout
        createLayout();

        // Styling improvements
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color lightGray = Color.darkGray;
        Color white = Color.white;

        // Apply fonts to input fields
        nameField.setFont(inputFont);
        addressField.setFont(inputFont);
        phoneField.setFont(inputFont);
        emailField.setFont(inputFont);

        nameField.setBackground(lightGray);
        addressField.setBackground(lightGray);
        phoneField.setBackground(lightGray);
        emailField.setBackground(lightGray);

        nameField.setForeground(white);
        addressField.setForeground(white);
        phoneField.setForeground(white);
        emailField.setForeground(white);

        // Apply font to buttons
        addButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        clearButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Pack the frame to ensure proper sizing
        pack();
    }

    private void initializeComponents() {
        contacts = new ArrayList<>();
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        nameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
    }

    private void createLayout() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);
        inputPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(Color.WHITE);
        inputPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        inputPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(emailField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        contactList.setBackground(Color.DARK_GRAY);
        contactList.setForeground(Color.white);
        JScrollPane scrollPane = new JScrollPane(contactList);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        mainPanel.setBackground(Color.BLACK);
        inputPanel.setBackground(Color.BLACK);
        buttonPanel.setBackground(Color.BLACK);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                    Contact contact = new Contact(name, address, phone, email);
                    contacts.add(contact);
                    contactListModel.addElement(contact.getName());

                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AddressBookGUI.this, "Please fill in all fields.");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Contact contact = contacts.get(selectedIndex);
                    contact.setName(nameField.getText());
                    contact.setAddress(addressField.getText());
                    contact.setPhone(phoneField.getText());
                    contact.setEmail(emailField.getText());
                    contactListModel.setElementAt(contact.getName(), selectedIndex);
                    clearFields();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    contacts.remove(selectedIndex);
                    contactListModel.remove(selectedIndex);
                    clearFields();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = JOptionPane.showInputDialog(AddressBookGUI.this, "Enter name to search:");
                if (searchName != null) {
                    for (int i = 0; i < contacts.size(); i++) {
                        if (contacts.get(i).getName().equalsIgnoreCase(searchName)) {
                            contactList.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        contactList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Contact selectedContact = contacts.get(selectedIndex);
                    String message = "Name: " + selectedContact.getName() +
                            "\nAddress: " + selectedContact.getAddress() +
                            "\nPhone: " + selectedContact.getPhone() +
                            "\nEmail: " + selectedContact.getEmail();
                    JOptionPane.showMessageDialog(AddressBookGUI.this, message, "Contact Details",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Styling improvements
        // Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = Color.DARK_GRAY; // button color
        Color buttonTextColor = Color.white;

        // Apply fonts to labels and input fields
        nameField.setFont(inputFont);
        addressField.setFont(inputFont);
        phoneField.setFont(inputFont);
        emailField.setFont(inputFont);

        // Apply font and color to buttons
        addButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        clearButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        addButton.setBackground(buttonColor);
        updateButton.setBackground(buttonColor);
        deleteButton.setBackground(buttonColor);
        searchButton.setBackground(buttonColor);
        clearButton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);

        addButton.setForeground(buttonTextColor);
        updateButton.setForeground(buttonTextColor);
        deleteButton.setForeground(buttonTextColor);
        searchButton.setForeground(buttonTextColor);
        clearButton.setForeground(buttonTextColor);
        exitButton.setForeground(buttonTextColor);

        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pack(); // Adjusts the frame size to fit the contents nicely
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddressBookGUI().setVisible(true);
            }
        });
    }

    private class Contact {
        private String name;
        private String address;
        private String phone;
        private String email;

        public Contact(String name, String address, String phone, String email) {
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
