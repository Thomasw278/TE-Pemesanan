package pemesanan.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

public class AppTest 
{

    //Panggil CLass FoodDelivery | User Account | Food Order
    private FoodDeliveryService foodDeliveryService;
    private UserAccount userAccount;
    private FoodOrder foodOrder;


    //Inisialisasi Awal 
    @BeforeEach
    void setUp() {
        //Inisialisasi Mockito
        userAccount = mock(UserAccount.class);
        foodDeliveryService = mock(FoodDeliveryService.class);
        
        //Inject Class Mockito ke Food Order ( Class yang diuji )
        foodOrder = new FoodOrder(userAccount, foodDeliveryService);
    }


    // Testing Order Food Valid
    @Test
    void Testing_Food_Order_Sukses(){

        // Inisialisasi Variabel Testing
        double harga = 750000;
        String food_item = "Nasi Goreng Menara";
        String address = "JL Kusbini No 5";

        //Cek Assert dimana harga barang harus > 0 || Nama Makanan tidak boleh kosong || Alamat Tidak Boleh Kosong
        assertTrue(harga > 0, "Harga harus lebih besar dari 0");
        assertTrue(food_item.length() > 0, "Pesanan Tidak Boleh Kosong");
        assertTrue(address.length() > 0, "Alamat / Address Tidak Boleh Kosong");

        // Cek Pakai When | Dan Asumsi True
        when(userAccount.hasSufficientBalance(harga)).thenReturn(true);

        // Proses Food Order
        foodOrder.placeOrder(food_item, harga, address);

        // Verifikasi Class UserAccount dan Food Delivery Service
        verify(userAccount, times(1)).hasSufficientBalance(harga);
        verify(foodDeliveryService, times(1)).deliverFood(food_item, address);

    };


    // Testing Order Food Tidak Valid
    @Test
    void Testing_Food_Order_Gagal(){

        // Inisialisasi Variabel Testing
        double harga = 750000;
        String food_item = "Nasi Goreng Menara";
        String address = "JL Kusbini No 5";

        //Cek Assert dimana harga barang harus > 0 || Nama Makanan tidak boleh kosong || Alamat Tidak Boleh Kosong
        assertTrue(harga > 0, "Harga harus lebih besar dari 0");
        assertTrue(food_item.length() > 0, "Pesanan Tidak Boleh Kosong");
        assertTrue(address.length() > 0, "Alamat / Address Tidak Boleh Kosong");

        // Cek Pakai When | Dan Asumsi false | Anggap Uang User Kurang
        when(userAccount.hasSufficientBalance(harga)).thenReturn(false);

        // Proses Food Order
        foodOrder.placeOrder(food_item, harga, address);

        // Verifikasi Class UserAccount dan Food Delivery Service
        verify(userAccount, times(1)).hasSufficientBalance(harga);
        verify(foodDeliveryService, never()).deliverFood(food_item, address);
    };

}