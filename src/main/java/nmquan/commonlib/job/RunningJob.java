package nmquan.commonlib.job;

public interface RunningJob {
    String getJobName();
    String getScheduleType();
    String getExpression();
    void runJob();
}
