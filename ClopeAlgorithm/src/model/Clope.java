package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is describing considering algorithm
 */
public class Clope {
    /**
     * Set of clusters that is need for completing gathering process
     */
    private List<Cluster> clusterSET = new ArrayList<>();

    /**
     *
     * @param clusters - Set of clusters
     * @param repulsionAttr - Repulsion attribute, inputs by the Human
     * @return The value of profit function or exit point
     */
    public static double getClusterProfit(List<Cluster> clusters, double repulsionAttr){
        try {
            double firstProfitValue = 0;
            double secondProfitValue = 0;
            for (Cluster cluster : clusters) {
                firstProfitValue = (cluster.getHeight() * cluster.getWidth() / Math.pow(cluster.getWidth(), repulsionAttr)) * cluster.getTransactionsAmount();
                secondProfitValue = cluster.getTransactionsAmount();
            }
            return firstProfitValue/secondProfitValue;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     *
     * @param transactionList - Set of transaction in cluster
     * @param repulsion - Repulsion attribute, inputs by the Human
     * @return - Set of clusters
     */
    public  List<Cluster> ClopeAlgorithm(List<Transaction> transactionList, double repulsion){
        for (Transaction transaction : transactionList) {
            Cluster newCluster = new Cluster(transaction);
            clusterSET.add(newCluster);
            double newClusterProfitFunctionValue = getClusterProfit(clusterSET, repulsion);
            double maxProfitFunctionValue = newClusterProfitFunctionValue;
            clusterSET.remove(newCluster);
            for (Cluster cluster : clusterSET) {
                cluster.addTransactionToCluster(transaction);
                double currentProfitFunctionValue = getClusterProfit(clusterSET, repulsion);
                if (maxProfitFunctionValue <= currentProfitFunctionValue) {
                    maxProfitFunctionValue = currentProfitFunctionValue;
                } else {
                    cluster.removeTransactionFromCluster(transaction);
                }
            }
            if (maxProfitFunctionValue == newClusterProfitFunctionValue) {
                clusterSET.add(newCluster);
            }
        }
        return clusterSET;
    }
}
