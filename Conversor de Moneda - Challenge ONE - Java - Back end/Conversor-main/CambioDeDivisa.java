import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CambioDeDivisa{

    private JFrame frame;
    private JButton btn;
    private JComboBox<Moneda> cmb;
    private JLabel lbl;
    private JTextField txt; // Se corrigió el tipo a JTextField.

    //tipos de modedas: ARG(equivale a peso argentino)
    public enum Moneda {
        arg_dolar, arg_euro, arg_libraEsterlina, arg_yenJapones, arg_WonSurCoreano, dolar_arg, euro_arg,
        libraEsterlina_arg, yenJapones_arg, WonSurCoreano_arg,
    }

    public double dolar = 872.25;
    public double euro = 929.33;
    public double libraEsterlina = 1076.88;
    public double yenJapones = 5.63;
    public double WonSurCoreano = 0.63;

    public double valorInput;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CambioDeDivisa window = new CambioDeDivisa();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CambioDeDivisa() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        txt = new JTextField(); // Inicializar 'texto'
        txt.setBounds(90, 71, 122, 20);
        frame.getContentPane().add(txt);
        txt.setColumns(10);

        cmb = new JComboBox<Moneda>();
        cmb.setModel(new DefaultComboBoxModel<>(Moneda.values()));
        cmb.setBounds(90, 102, 122, 22);
        frame.getContentPane().add(cmb);

        btn = new JButton("Convertir");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Convertir();
            }
        });
        btn.setBounds(222, 102, 89, 23);
        frame.getContentPane().add(btn);

        lbl = new JLabel("00.00");
        lbl.setBounds(222, 74, 191, 14);
        frame.getContentPane().add(lbl);
    }

    public void Convertir() {
        if (Validar(txt.getText())) {
            Moneda moneda = (Moneda) cmb.getSelectedItem();
            switch (moneda) {
                case arg_dolar:
                    PesoAMoneda(dolar);
                    break;
                case arg_euro:
                    PesoAMoneda(euro);
                    break;
                case arg_libraEsterlina:
                    PesoAMoneda(libraEsterlina);
                    break;
                case arg_yenJapones:
                    PesoAMoneda(yenJapones);
                    break;
                case arg_WonSurCoreano:
                    PesoAMoneda(WonSurCoreano);
                    break;
                case dolar_arg:
                    MonedaAPeso(dolar);
                    break;
                case euro_arg:
                    MonedaAPeso(euro);
                    break;
                case libraEsterlina_arg:
                    MonedaAPeso(libraEsterlina);
                    break;
                case yenJapones_arg:
                    MonedaAPeso(yenJapones);
                    break;
                case WonSurCoreano_arg:
                    MonedaAPeso(WonSurCoreano);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + moneda);
            }
        }
    }

    public void PesoAMoneda(double moneda) {
        double res = valorInput / moneda;
        lbl.setText(Redondear(res));
    }

    public void MonedaAPeso(double moneda) {
        double res = valorInput * moneda;
        lbl.setText(Redondear(res));
    }

    public String Redondear(double valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(valor);
    }

    public boolean Validar(String texto) {
        try {
            double x = Double.parseDouble(texto);
            if (x > 0) {
                valorInput = x;
                return true;
            } else {
                lbl.setText("Ingrese un número positivo");
                return false;
            }
        } catch (NumberFormatException e) {
            lbl.setText("¡Solamente números!");
            return false;
        }
    }
}
