package logAnalyze.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import logAnalyze.storm.bolt.MessageFilterBolt;
import logAnalyze.storm.bolt.ProcessMessage;
import logAnalyze.storm.spout.RandomSpout;

/**
 * Describe: 驱动类
 */
public class LogAnalyzeTopologyMain {
    public static void main(String[] args) throws  Exception{
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka-spout", new RandomSpout(), 2);//获取外部数据源信息
        builder.setBolt("MessageFilter-bolt",new MessageFilterBolt(),3).shuffleGrouping("kafka-spout");//随机分组消费数据
        builder.setBolt("ProcessMessage-bolt",new ProcessMessage(),2).fieldsGrouping("MessageFilter-bolt", new Fields("type"));
        Config topologConf = new Config();
        if (args != null && args.length > 0) {
            topologConf.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(args[0], topologConf, builder.createTopology());
        } else {
            topologConf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("LogAnalyzeTopologyMain", topologConf, builder.createTopology());
            Utils.sleep(10000000);
            cluster.shutdown();
        }
    }
}
