//$Header: /cvsroot-fuse/mec-as2/b47/de/mendelson/comm/as2/client/ListCellRendererSignature.java,v 1.1 2015/01/06 11:07:37 heller Exp $
package de.mendelson.comm.as2.client;

import de.mendelson.comm.as2.message.ResourceBundleAS2Message;
import de.mendelson.util.MecResourceBundle;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 * Renderer to render the encryption entries in comboboxes
 *
 * @author S.Heller
 * @version $Revision: 1.1 $
 */
public class ListCellRendererSignature extends JLabel implements ListCellRenderer {

    private MecResourceBundle rbMessage;

    /**
     * Constructs a default renderer object for an item in a list.
     */
    public ListCellRendererSignature() {
        super();
        setOpaque(true);
        try {
            rbMessage = (MecResourceBundle) ResourceBundle.getBundle(
                    ResourceBundleAS2Message.class.getName());
        } catch (MissingResourceException e) {
            throw new RuntimeException("Oops..resource bundle " + e.getClassName() + " not found.");
        }
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void validate() {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void revalidate() {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void repaint(Rectangle r) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Strings get interned...
        if (propertyName.equals("text")) {
            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, char oldValue, char newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, short oldValue, short newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, long oldValue, long newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, float oldValue, float newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, double oldValue, double newValue) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }

    /**
     * A subclass of DefaultListCellRenderer that implements UIResource.
     * DefaultListCellRenderer doesn't implement UIResource directly so that
     * applications can safely override the cellRenderer property with
     * DefaultListCellRenderer subclasses.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with future Swing
     * releases. The current serialization support is appropriate for short term
     * storage or RMI between applications running the same version of Swing. As
     * of 1.4, support for long term storage of all
     * JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package. Please see
     * {@link java.beans.XMLEncoder}.
     */
    public static class UIResource extends DefaultListCellRenderer
            implements javax.swing.plaf.UIResource {
    }

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setComponentOrientation(list.getComponentOrientation());
        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());

        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }
        this.setFont(list.getFont());
        //Linux sets the value to null if nothing has been selected in the combobox
        if (value != null) {
            if (value instanceof Integer) {
                this.setEnabled(list.isEnabled());
                this.setText(this.rbMessage.getResourceString("signature." + String.valueOf(value)));
            }
        }
        this.setHorizontalAlignment(SwingConstants.LEADING);
        this.setHorizontalTextPosition(SwingConstants.RIGHT);

        return (this);
    }
}
