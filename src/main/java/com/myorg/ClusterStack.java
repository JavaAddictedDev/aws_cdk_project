/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.constructs.Construct;

/**
 *
 * @author rengoku
 */
public class ClusterStack extends Stack {

    public ClusterStack(final Construct scope, final String id, Vpc vpc) {
        this(scope, id, vpc, null);
    }

    public ClusterStack(final Construct scope, final String id, Vpc vpc, final StackProps props) {
        super(scope, id, props);

        Cluster.Builder.create(this, id)
                .clusterName("cluster-01")
                .vpc(vpc)
                .build();
    }
}
