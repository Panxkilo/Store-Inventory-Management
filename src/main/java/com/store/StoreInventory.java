package com.store;

import java.util.ArrayList;
import java.util.List;

/**
 * Maneja la coleccion de productos en memoria.
 * La lista es privada: solo se toca a traves de los metodos publicos.
 */
public class StoreInventory {

    private final List<Product> products = new ArrayList<>();

    /** Agrega un producto al inventario. */
    public void addProduct(Product product) {
        products.add(product);
    }

    /** Devuelve una copia de la lista de productos (para no exponer la lista interna). */
    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Elimina un producto por nombre (busqueda sin distinguir mayusculas/minusculas).
     * Devuelve true si se elimino alguno, false si no habia coincidencia.
     */
    public boolean removeProduct(String productName) {
        return products.removeIf(product -> product.getName().equalsIgnoreCase(productName));
    }

    /** Devuelve la lista de productos de la categoria indicada (sin distinguir mayusculas/minusculas). */
    public List<Product> searchByCategory(String category) {
        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                matches.add(product);
            }
        }
        return matches;
    }

    /** Muestra todos los productos en pantalla usando el toString() de Product. */
    public void listInventory() {
        if (products.isEmpty()) {
            System.out.println("El inventario esta vacio.");
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
    }

    /**
     * Procesa una orden: valida stock, lo descuenta, calcula el descuento por volumen
     * y devuelve un OrderSummary con el resultado.
     */
    public OrderSummary createOrder(String productName, int quantity) {
        // 1. El producto debe existir.
        Product product = findByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("No existe un producto llamado: " + productName);
        }
        // ...y debe haber stock suficiente para la cantidad pedida.
        if (quantity > product.getStockQuantity()) {
            throw new IllegalStateException(
                    "Stock insuficiente para " + productName
                    + ". Pedido: " + quantity + ", disponible: " + product.getStockQuantity());
        }

        // 2. Descontar la cantidad pedida del stock.
        product.setStockQuantity(product.getStockQuantity() - quantity);

        // 3. Subtotal antes de descuento.
        double unitPrice = product.getPrice();
        double subtotal = unitPrice * quantity;

        // 4. Descuento por volumen segun el subtotal (tramos del brief).
        double discountRate = discountRateFor(subtotal);

        // 5. Total final ya con el descuento aplicado.
        double finalTotal = subtotal * (1 - discountRate);

        return new OrderSummary(productName, quantity, unitPrice, discountRate, finalTotal);
    }

    /** Regla de descuento por tramos de subtotal. Cada tramo se ve de un vistazo. */
    private double discountRateFor(double subtotal) {
        if (subtotal >= 500) {
            return 0.15; // >= $500 -> 15%
        } else if (subtotal >= 200) {
            return 0.10; // $200 - $499.99 -> 10%
        } else if (subtotal >= 100) {
            return 0.05; // $100 - $199.99 -> 5%
        } else {
            return 0.0;  // < $100 -> 0%
        }
    }

    /** Busca un producto por nombre (case-insensitive). Devuelve null si no lo encuentra. */
    private Product findByName(String productName) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
}
