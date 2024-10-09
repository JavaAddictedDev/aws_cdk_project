/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

/**
 *
 * @author rengoku
 */
public class VpcStack extends Stack {
    
    public VpcStack(final Construct scope, final String id) {
        this(scope, id, null);
    }
    
    public VpcStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        
        Vpc.Builder.create(this, "Vpc01")
                .maxAzs(2)
                .natGateways(0)
                .build();
    }
}
