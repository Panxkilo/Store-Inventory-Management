package com.store;

/**
 * Resumen estructurado del resultado de una orden.
 * Se usa un record por ser un simple contenedor de datos inmutable,
 * mas claro para explicar que un Map generico.
 */
public record OrderSummary(
        String productName,
        int quantity,
        double unitPrice,
        double discountRate,
        double finalTotal) {

    @Override
    public String toString() {
        // Ejemplo: "Laptop x2 - Unit: $999.99 | Discount: 15% | Total: $1699.98"
        // Locale.US para que el separador decimal sea siempre punto.
        return String.format(java.util.Locale.US,
                "%s x%d - Unit: $%.2f | Discount: %.0f%% | Total: $%.2f",
                productName, quantity, unitPrice, discountRate * 100, finalTotal);
    }
}
