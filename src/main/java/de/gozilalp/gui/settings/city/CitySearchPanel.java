package de.gozilalp.gui.settings.city;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CitySearchPanel extends JPanel {
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search?format=json&limit=5&q=";
    private final JTextField cityInput;
    private final JList<String> suggestionList;
    private final DefaultListModel<String> listModel;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private long lastRequestTime = 0;

    public CitySearchPanel() {
        setLayout(new BorderLayout());

        cityInput = new JTextField(20);
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(suggestionList);

        // Styling für die Vorschlagsliste
        scrollPane.setPreferredSize(new Dimension(250, 100));
        suggestionList.setVisible(false);

        add(cityInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Event Listener für Texteingabe
        cityInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { delayedSearchCities(); }
            @Override
            public void removeUpdate(DocumentEvent e) { delayedSearchCities(); }
            @Override
            public void changedUpdate(DocumentEvent e) { delayedSearchCities(); }
        });

        // Event Listener für Listenauswahl
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!suggestionList.isSelectionEmpty()) {
                    cityInput.setText(suggestionList.getSelectedValue());
                    suggestionList.setVisible(false);
                }
            }
        });

        // Event Listener für Enter-Taste
        cityInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    suggestionList.requestFocus();
                    suggestionList.setSelectedIndex(0);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && listModel.size() > 0) {
                    cityInput.setText(listModel.getElementAt(0));
                    suggestionList.setVisible(false);
                }
            }
        });
    }

    // Verzögerte Suche
    private void delayedSearchCities() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRequestTime < 300) {
            return; // Verhindere zu schnelle Anfragen
        }
        lastRequestTime = currentTime;
        String query = cityInput.getText().trim();
        if (query.length() < 2) {
            suggestionList.setVisible(false);
            return;
        }

        executorService.submit(() -> {
            List<String> suggestions = getCitySuggestions(query);
            SwingUtilities.invokeLater(() -> {
                listModel.clear();
                for (String city : suggestions) {
                    listModel.addElement(city);
                }
                suggestionList.setVisible(!suggestions.isEmpty());
            });
        });
    }

    private List<String> getCitySuggestions(String query) {
        List<String> cities = new ArrayList<>();
        try {
            String encodedQuery = java.net.URLEncoder.encode(query, StandardCharsets.UTF_8);
            // Hier wird der Parameter `countrycodes=DE,US,CA,GB,FR,IT` hinzugefügt, um nur Städte aus den angegebenen Ländern zu bevorzugen
            URI uri = new URI(NOMINATIM_URL + encodedQuery + "&countrycodes=DE,US,CA,GB,FR,IT");

            try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(uri))) {
                String jsonResponse = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);

                for (JsonNode node : rootNode) {
                    String cityName = node.get("display_name").asText();
                    cities.add(cityName);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public String getSelectedCity() {
        String selectedCity = cityInput.getText().trim();
        if (selectedCity.isEmpty() || !listModel.contains(selectedCity)) {
            return null;
        }
        return selectedCity;
    }
}
