import java.io.*;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

public class ProveedorAPI {

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static BigDecimal obtenerTasaCambio(String monedaBase, String monedaDestino) {
        String url = "https://v6.exchangerate-api.com/v6/3bd4e1604db49f1d47baab5b/latest/USD";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer TU_TOKEN_DE_API_DEL_BCRA")
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Error al obtener la respuesta: " + response);
            }

            String responseBody = response.body().string();
            response.body().close();
            return obtenerTasa(responseBody);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión con la API del BCRA");
            throw new RuntimeException("Error en la conexión: " + ex);
        }
    }

    private static BigDecimal obtenerTasa(String responseBody) {
        JSONObject json = new JSONObject(responseBody);
        BigDecimal tasa = json.getBigDecimal("v");
        return tasa;
    }
}
