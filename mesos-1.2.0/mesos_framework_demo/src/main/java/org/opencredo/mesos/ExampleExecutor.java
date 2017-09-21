package org.opencredo.mesos;

import org.apache.mesos.Executor;
import org.apache.mesos.ExecutorDriver;
import org.apache.mesos.MesosExecutorDriver;
import org.apache.mesos.Protos;
import java.io.IOException;

public class ExampleExecutor implements Executor {
        public int count_yy = 0;

	@Override
	public void registered(ExecutorDriver executorDriver,
			Protos.ExecutorInfo executorInfo,
			Protos.FrameworkInfo frameworkInfo, Protos.SlaveInfo slaveInfo) {
	}

	@Override
	public void reregistered(ExecutorDriver executorDriver,
			Protos.SlaveInfo slaveInfo) {
	}

	@Override
	public void disconnected(ExecutorDriver executorDriver) {
	}

	@Override
	public void launchTask(ExecutorDriver executorDriver,
			Protos.TaskInfo taskInfo) {

		Integer id = Integer.parseInt(taskInfo.getData().toStringUtf8());
		String reply = id.toString();
		executorDriver.sendFrameworkMessage(reply.getBytes());
		Protos.TaskStatus status = Protos.TaskStatus.newBuilder()
				.setTaskId(taskInfo.getTaskId())
				.setState(Protos.TaskState.TASK_FINISHED).build();
		executorDriver.sendStatusUpdate(status);
                count_yy++;
                System.out.println("executor lauching .............ccccc");

        if(count_yy > 1)
            return;
        try {
           System.out.println("executor lauching .............ccccc 2222");
             Runtime.getRuntime().exec("echo 1111111111111111 > /tt11");
            Runtime.getRuntime().exec("/lxc_start.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }	
     }

	@Override
	public void killTask(ExecutorDriver executorDriver, Protos.TaskID taskID) {

	}

	@Override
	public void frameworkMessage(ExecutorDriver executorDriver, byte[] bytes) {

	}

	@Override
	public void shutdown(ExecutorDriver executorDriver) {

	}

	@Override
	public void error(ExecutorDriver executorDriver, String s) {

	}

	public static void main(String[] args) throws Exception {
		MesosExecutorDriver driver = new MesosExecutorDriver(
				new ExampleExecutor());
		System.exit(driver.run() == Protos.Status.DRIVER_STOPPED ? 0 : 1);
	}
}
