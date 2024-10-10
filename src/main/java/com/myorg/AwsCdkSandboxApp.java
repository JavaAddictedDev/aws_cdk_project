package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class AwsCdkSandboxApp {

    public static void main(final String[] args) {
        App app = new App();

        Environment environment = Environment.builder()
                .region("us-east-2")
                .build();

        StackProps props = StackProps.builder()
                .env(environment)
                .build();

        VpcStack vpcStack = new VpcStack(app, "Vpc", props);

        ClusterStack clusterStack = new ClusterStack(app, "Cluster", vpcStack.getInstance(), props);

        clusterStack.addDependency(vpcStack);
        
        Service01Stack service01Stack = new Service01Stack(app, "Service01",  clusterStack.getCluster());
        service01Stack.addDependency(clusterStack);

        app.synth();
    }
}
