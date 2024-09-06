package es.merlinsoftware;

import es.merlinsoftware.pojo.ProductSales;
import es.merlinsoftware.pojo.ProductStock;

import java.util.*;
import java.util.stream.Stream;

/**
 * Para esta solución en vez de aplicar los pesos al stock y las ventas y sumarlos directamente,
 * saco los porcentages de ambos, les aplico el peso y los sumo.
 */

public class Solution2 {

    public static List<Long> sortProductsByScores(int stockWeight, int salesWeight,
                                                  List<ProductStock> productsStockInformation,
                                                  List<ProductSales> productsSalesInformation) {

        long allProductStock = 0L;
        for(ProductStock productStock:productsStockInformation) {
            allProductStock += productStock.getAvailableStock();
        }

        long allProductSales = 0L;
        for(ProductSales productSales:productsSalesInformation) {
            allProductSales += productSales.getSalesAmount();
        }

        Map<Long, Double> stockPercentage = new HashMap<>();
        for(ProductStock productStock:productsStockInformation) {
            stockPercentage.put(productStock.getProductId(), (double) (productStock.getAvailableStock() * 100) / allProductStock);
        }

        Map<Long, Double> salesPercentage = new HashMap<>();
        for(ProductSales productSales:productsSalesInformation) {
            salesPercentage.put(productSales.getProductId(), (productSales.getSalesAmount() * 100) / allProductSales);
        }

        Map<Long, Double> productsIdWithPercentages = new HashMap<>();
        for(Long productId:stockPercentage.keySet()) {
            double stockPercentageWeighted = (stockPercentage.get(productId) * stockWeight) / 100;
            if (salesPercentage.containsKey(productId)) {
                double salesPercentageWeighted = (salesPercentage.get(productId) * salesWeight) / 100;
                productsIdWithPercentages.put(productId, stockPercentageWeighted + salesPercentageWeighted);
            } else {
                productsIdWithPercentages.put(productId, stockPercentageWeighted);
            }
        }

        System.out.println(productsIdWithPercentages);

        Stream<Map.Entry<Long, Double>> sortedByPercentage = productsIdWithPercentages.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<Long> solution = new ArrayList<>();
        sortedByPercentage.forEach((entry) -> {
            solution.add(entry.getKey());
        });

        System.out.println(solution);

        return solution;
    }
}
