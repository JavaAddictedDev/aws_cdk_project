package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import software.amazon.awscdk.services.ec2.Vpc;

public class AwsCdkSandboxApp {

    public static void main(final String[] args) {
        App app = new App();

        StackProps vpcProps = StackProps.builder()
                .env(Environment.builder()
                        .region("us-east-2")
                        .build())
                .build();
        
        VpcStack vpcStack = new VpcStack(app, "Vpc", vpcProps);
        
        ClusterStack clusterStack = new ClusterStack(app, "Cluster", vpcStack.getInstance());
        
        clusterStack.addDependency(vpcStack);
        
        app.synth();
    }
}
