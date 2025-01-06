import static org.junit.jupiter.api.Assertions.*;

import orderCalculator.OrderCalculator;
import org.junit.jupiter.api.Test;

public class OrderCalculatorTest {

    //CASI ECCEZIONALI

    // 1. productType null -> IllegalArgumentException
    @Test
    void testNullProductType() {
        assertThrows(IllegalArgumentException.class, () -> OrderCalculator.calculateTotalPrice(null, 10, 5.0), "productType null dovrebbe lanciare un IllegalArgumentException");
    }

    // 2. productType vuoto -> IllegalArgumentException
    @Test
    void testEmptyProductType() {
        assertThrows(IllegalArgumentException.class, () -> OrderCalculator.calculateTotalPrice("", 10, 5.0), "productType null dovrebbe lanciare un IllegalArgumentException");
    }

    // 3. quantity = 0 -> IllegalArgumentException
    @Test
    void testZeroQuantity() {
        assertThrows( IllegalArgumentException.class, () -> OrderCalculator.calculateTotalPrice("food", 0, 10.0), "quantity = 0 dovrebbe lanciare un IllegalArgumentException");
    }

    // 4. quantity negativo -> IllegalArgumentException
    @Test
    void testNegativeQuantity() {
        assertThrows( IllegalArgumentException.class, () -> OrderCalculator.calculateTotalPrice("food", -5, 10.0), "quantity < 0 dovrebbe restituire -1");
    }

    // 5. unitPrice = 0 -> IllegalArgumentException
    @Test
    void testZeroUnitPrice() {
        assertThrows( IllegalArgumentException.class, () -> OrderCalculator.calculateTotalPrice("food", 10, 0.0), "unitPrice = 0 dovrebbe restituire -1");
    }

    // 6. unitPrice negativo -> IllegalArgumentException
    @Test
    void testNegativeUnitPrice() {
        assertThrows( IllegalArgumentException.class, () -> OrderCalculator.calculateTotalPrice("food", 10, -5.0), "unitPrice < 0 dovrebbe restituire -1");
    }


    //BOUNDARY CASES

    // 7. Nessuno sconto (quantity < 10), productType = "vehicles"
    @Test
    void testNoDiscountDefault() {
        // quantity=9, unitPrice=2
        // base=18, sconto=0, tax=5% => 18.9
        double result = OrderCalculator.calculateTotalPrice("vehicles", 9, 2.0);
        assertEquals(18.9, result, 0.0001);
    }

    // 8. Sconto 5% (10 <= quantity < 50), productType = "food"
    @Test
    void testDiscount5Food() {
        // quantity=10, unitPrice=2 => base=20
        // sconto=5% => 19
        // tax=4% => 19.76
        double result = OrderCalculator.calculateTotalPrice("food", 10, 2.0);
        assertEquals(19.76, result, 0.0001);
    }

    //9. Sconto 5% (10 <= quantity < 50), productType = "vehicles"
    @Test
    void testDiscount5Default(){
        //quantity=49, unitPrice=2 => base=98
        //sconto=5% => 93.1
        //tax=5% => 97.755
        double result = OrderCalculator.calculateTotalPrice("vehicles", 49, 2.0);
        assertEquals(97.76, result, 0.0001);
    }

    //10. Sconto 10% (50 <= quantity < 100), productType = "vehicles"
    @Test
    void testDiscount10Default(){
        //quantity=50, unitPrice=20 => base=1000
        //sconto=10% => 900
        //tax=5% => 945
        double result = OrderCalculator.calculateTotalPrice("vehicles", 50, 20);
        assertEquals(945, result, 0.0001);
    }

    //11. Sconto 15% (quantity >= 100), productType = "vehicles"
    @Test
    void testDiscount15Default(){
        //quantity=100, unitPrice=173,2 => base=17320
        //sconto=15% => 14722
        //tax=5% => 15458.1
        double result = OrderCalculator.calculateTotalPrice("vehicles", 100, 173.2);
        assertEquals(15458.1, result, 0.0001);
    }


    //CASI CHE PREVEDONO TUTTI I TIPI DI PRODOTTI


    //12. Sconto 0% (quantity < 10), productType = "food"
    @Test
    void testNoDiscountFood(){
        //quantity=9, unitPrice=2 => base=18
        //sconto=0% => 18
        //tax=4% => 18.72
        double result = OrderCalculator.calculateTotalPrice("food", 9, 2.0);
        assertEquals(18.72, result, 0.0001);
    }

    //13. Sconto 0% (quantity < 10), productType = "clothing"
    @Test
    void testNoDiscountClothing(){
        //quantity=6, unitPrice=2 => base=12
        //sconto=0% => 12
        //tax=10% => 13.2
        double result = OrderCalculator.calculateTotalPrice("clothing", 6, 2.0);
        assertEquals(13.2, result, 0.0001);
    }

    //14. Sconto 0% (quantity < 10), productType = "electronics"
    @Test
    void testNoDiscountElectronics(){
        //quantity=3, unitPrice=115.24 => base=345.72
        //sconto=0% => 345.72
        //tax=22% => 421.7784
        double result = OrderCalculator.calculateTotalPrice("electronics", 3, 115.24);
        assertEquals(421.78, result, 0.0001);
    }


    //COMBINAZIONI DI TIPO DI PRODOTTO E QUANTITA'


    //15. Sconto 15% (quantity >= 100), productType = "electronics"
    @Test
    void testDiscount15Food(){
        //quantity=170, unitPrice=1.79 => base=304,3
        //sconto=15% => 258.655
        //tax=22% => 315.5591
        double result = OrderCalculator.calculateTotalPrice("electronics", 170, 1.79);
        assertEquals(315.56, result, 0.0001);
    }

    //16. Sconto 10% (50 <= quantity < 100), productType = "clothing"
    @Test
    void testDiscount10Clothing(){
        //quantity=77, unitPrice=53.41 => base=4112.57
        //sconto=10% => 3701.313
        //tax=10% => 4071,4443
        double result = OrderCalculator.calculateTotalPrice("clothing", 77, 53.41);
        assertEquals(4071.44, result, 0.0001);
    }


    //CASI SPECIALI

    //17. Tipologia prodotto con caratteri in maiuscolo e minuscolo
    @Test
    void testProdottoMaiuscoleEMinuscole() {
        // quantity=1, unitPrice=50
        // base=50, sconto=0
        // tax=22% => 61
        double result = OrderCalculator.calculateTotalPrice("EleCtroNics", 1, 50);
        assertEquals(61, result, 0.0001);
    }

    // 18. Prezzo con molte cifre decimali (per testare l'arrotondamento)
    @Test
    void testPrezzoMolteCifreDecimali() {
        // quantity=1, unitPrice=3.14159
        // base=3.14159, sconto=0
        // productType="clothing" => tax=10% => 3.455749
        // arrotondato => 3.46
        double result = OrderCalculator.calculateTotalPrice("clothing", 1, 3.14159);
        assertEquals(3.46, result, 0.0001);
    }


    // 19. unitPrice molto piccolo (verifica arrotondamenti)
    @Test
    void testPrezzoProssimoAlloZero() {
        // quantity=10, unitPrice=0.01 => base=0.10
        // sconto=5% => 0.095
        // tax=4% => 0.0988 => arrotondato a 0.10
        double result = OrderCalculator.calculateTotalPrice("food", 10, 0.01);
        assertEquals(0.10, result, 0.0001);
    }


}
