package orderCalculator;

public class OrderCalculator {

    /**
     * Calcola il prezzo totale di un ordine, applicando eventuali regole di sconto e di tassazione
     * in base al tipo di prodotto e alla quantità acquistata.
     *
     * @param productType Tipo di prodotto (es. "food", "electronics", "clothing", ...).
     * @param quantity    Quantità ordinata (deve essere >= 1 per un calcolo valido).
     * @param unitPrice   Prezzo unitario del prodotto (deve essere > 0).
     * @return            Prezzo totale calcolato, arrotondato a due decimali per eccesso.;
     *                    Lancia un IllegalArgumentException se i parametri sono invalidi
     *                    (ad es. quantity <= 0 o unitPrice <= 0).
     */
    public static double calculateTotalPrice(String productType, int quantity, double unitPrice) {

        // 1. Validazione parametri di ingresso
        if (productType == null || productType.isEmpty() || quantity <= 0 || unitPrice <= 0) {
            // Lanciamo un'eccezione se i parametri di ingresso non sono validi
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        // 2. Calcolo del prezzo base
        double basePrice = quantity * unitPrice;

        // 3. Scontistica a scaglioni
        //    - 5% di sconto se  10 <= quantity < 50
        //    - 10% di sconto se 50 <= quantity < 100
        //    - 15% di sconto se quantity >= 100
        double discountRate = 0.0;
        if (quantity >= 10 && quantity < 50) {
            discountRate = 0.05;
        } else if (quantity >= 50 && quantity < 100) {
            discountRate = 0.10;
        } else if (quantity >= 100) {
            discountRate = 0.15;
        }
        double discountedPrice = basePrice * (1 - discountRate);

        // 4. Tassazione in base al tipo di prodotto
        //    - "food" -> 4%
        //    - "electronics" -> 22%
        //    - "clothing" -> 10%
        //    - default -> 5%
        double taxRate;
        switch (productType.toLowerCase()) {
            case "food":
                taxRate = 0.04;
                break;
            case "electronics":
                taxRate = 0.22;
                break;
            case "clothing":
                taxRate = 0.10;
                break;
            default:
                taxRate = 0.05;
        }
        double finalPrice = discountedPrice * (1 + taxRate);

        // 5. Ritorno del prezzo finale arrotondato a due decimali
        return Math.round(finalPrice * 100.0) / 100.0;
    }
}

