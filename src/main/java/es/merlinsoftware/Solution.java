package es.merlinsoftware;

import es.merlinsoftware.pojo.ProductSales;
import es.merlinsoftware.pojo.ProductStock;
import java.util.*;
import java.util.stream.Stream;

public class Solution {

    public static List<Long> sortProductsByScores(int stockWeight, int salesWeight,
                                                  List<ProductStock> productsStockInformation,
                                                  List<ProductSales> productsSalesInformation) {

        //TODO: Complete this method with the solution, you don't need to split your solution over multiple classes,
        // just get it done!

        Map<Long, Double> salesInfoMap = new HashMap<>();
        for (ProductSales productSales: productsSalesInformation) {
            salesInfoMap.put(productSales.getProductId(), productSales.getSalesAmount());
        }

        Map<Long, Double> productsIdWithPercentages = new HashMap<>();
        for (ProductStock productStock: productsStockInformation) {
            double stockPercentageWeighted =  (double) (productStock.getAvailableStock() * stockWeight) / 100;
            if(salesInfoMap.containsKey(productStock.getProductId())) {
                double salesPercentageWeighted = (salesInfoMap.get(productStock.getProductId()) * salesWeight) / 100;
                productsIdWithPercentages.put(productStock.getProductId(), stockPercentageWeighted + salesPercentageWeighted);
            } else {
                productsIdWithPercentages.put(productStock.getProductId(), stockPercentageWeighted);
            }
        }

        Stream<Map.Entry<Long, Double>> sortedByPercentage = productsIdWithPercentages.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<Long> solution = new ArrayList<>();
        sortedByPercentage.forEach((entry) -> solution.add(entry.getKey()));

        return solution;
    }
}
