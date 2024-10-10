package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.CpuUtilizationScalingProps;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.ScalableTaskCount;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
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

        service01.getTargetGroup().configureHealthCheck(
                HealthCheck.builder()
                        .path("/actuator/health")
                        .port("8080")
                        .healthyHttpCodes("200").build()
        );

        ScalableTaskCount scalableTaskCount = service01.getService().autoScaleTaskCount(
                EnableScalingProps.builder()
                        .minCapacity(2)
                        .maxCapacity(4)
                        .build());
        
        scalableTaskCount.scaleOnCpuUtilization("Service01AutoScaling", CpuUtilizationScalingProps.builder()
        .targetUtilizationPercent(50)
        .scaleInCooldown(Duration.seconds(60))
        .scaleOutCooldown(Duration.seconds(60))
        .build());
    }
}
