package com.myorg;

import software.amazon.awscdk.RemovalPolicy;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.logs.LogGroup;

public class Service01Stack extends Stack {
    
    public Service01Stack(final Construct scope, final String id, Cluster cluster) {
        this(scope, id, cluster, null);
    }
    
    public Service01Stack(final Construct scope, final String id, Cluster cluster, final StackProps props) {
        super(scope, id, props);
        
        ApplicationLoadBalancedFargateService service01 = ApplicationLoadBalancedFargateService.Builder.create(this, "ALB01").
                serviceName("service-01")
                .cluster(cluster)
                .cpu(512)
                .desiredCount(1)
                .memoryLimitMiB(1024)
                .listenerPort(8889)
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("aws_project_01")
                                .image(ContainerImage.fromRegistry("siecola/curso_aws_project01:1.0.0"))
                                .containerPort(8080)
                                .logDriver(
                                        LogDriver.awsLogs(
                                                AwsLogDriverProps.builder()
                                                        .logGroup(
                                                                LogGroup.Builder.create(this, "Service01LogGroup")
                                                                        .logGroupName("Service01")
                                                                        .removalPolicy(RemovalPolicy.DESTROY)
                                                                        .build())
                                                        .streamPrefix("Service01")
                                                        .build()))
                                .build())
                .publicLoadBalancer(Boolean.TRUE)
                .build();
    }
}
