package com.aorura.cwcalculator;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.JDatePanel;

/**
 * Construct components with a custom date model.
 *
 * a. Be able to create a component with a custom date model.
 * b. Monitor changes on the custom date model, only get one notification with change listener.
 */
public class Feature3 {
	

    public static void main(final String[] args) {
    	final JTextField todayCW, selectedDayCW;
    	final String todayCWLabel = "Today: ";
    	final String selectedCWLabel =  "SelectedDay: ";
    	final String CW = " CW";
    	final String APP_NAME = "Week of Year";
        // Create a frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 260);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }        
         });
        frame.setTitle(APP_NAME);

        // Create a flow layout panel
        final JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        
        todayCW = new JTextField();
        todayCW.setHorizontalAlignment(SwingConstants.CENTER);
        selectedDayCW = new JTextField();
        todayCW.setText(getTodayCW());
        selectedDayCW.setHorizontalAlignment(SwingConstants.CENTER);

        // Create the JDatePanel
        final JDatePanel datePanel2 = new JDatePanel(new DemoDateModel(null));
        datePanel2.getModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                DemoDateModel source = (DemoDateModel) e.getSource();
                Calendar calendar = new GregorianCalendar(Locale.GERMAN);
                calendar.set(source.getYear(),source.getMonth(),source.getDay());
                selectedDayCW.setText(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
            }
        });
        datePanel2.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        datePanel2.setBounds(10, 10, 10, 10);
        panel.add(datePanel2);
        
        panel.add(Box.createRigidArea(new Dimension(5,0)));

        JLabel todayLabel = new JLabel(todayCWLabel);
        JLabel selectedLabel = new JLabel(selectedCWLabel);
        todayLabel.setPreferredSize(new Dimension(80, todayLabel.getMinimumSize().height));
        selectedLabel.setPreferredSize(new Dimension(80, selectedLabel.getMinimumSize().height));
        todayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        selectedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(150, 80));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        
        JPanel todayCWPanel = new JPanel();
        todayCWPanel.setLayout(new BoxLayout(todayCWPanel, BoxLayout.X_AXIS));
        todayCWPanel.add(todayLabel);
        todayCWPanel.add(todayCW);
        todayCWPanel.add(new JLabel(CW));
        todayCWPanel.add(Box.createRigidArea(new Dimension(5,0)));
        
        JPanel selectedCWPanel = new JPanel();
        selectedCWPanel.setLayout(new BoxLayout(selectedCWPanel, BoxLayout.X_AXIS));
        selectedCWPanel.add(selectedLabel);
        selectedCWPanel.add(selectedDayCW);
        selectedCWPanel.add(new JLabel(CW));
        selectedCWPanel.add(Box.createRigidArea(new Dimension(5,0)));
        
        rightPanel.add(todayCWPanel);
        rightPanel.add(selectedCWPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(5,0)));
        
        panel.add(rightPanel);

        // Make the frame visible
        frame.pack();
        frame.setVisible(true);
    }
    
    public static String getTodayCW() {
    	Date today = new Date();
    	Calendar calendar = new GregorianCalendar(Locale.GERMAN);
    	
    	calendar.setTime(today);
    	
    	return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
    }

    public static class DemoDateModel extends AbstractDateModel<String> {

        private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        public DemoDateModel(String value) {
            super();
            setValue(value);
        }

        @Override
        protected String fromCalendar(Calendar from) {
            return sdf.format(from.getTime());
        }

        @Override
        protected Calendar toCalendar(String from) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(from));
                return c;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
