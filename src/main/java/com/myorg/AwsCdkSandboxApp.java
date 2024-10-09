package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class AwsCdkSandboxApp {

    public static void main(final String[] args) {
        App app = new App();

        StackProps vpcProps = StackProps.builder()
                .env(Environment.builder()
                        .region("us-east-2")
                        .build())
                .build();
        
        new VpcStack(app, "Vpc", vpcProps);

        app.synth();
    }
}
