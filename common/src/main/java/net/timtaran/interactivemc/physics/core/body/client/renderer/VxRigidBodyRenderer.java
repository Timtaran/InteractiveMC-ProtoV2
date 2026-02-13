package net.timtaran.interactivemc.physics.core.body.client.renderer;/*
 * This file is part of Velthoric.
 * Licensed under LGPL 3.0.
 */
<<<<<<<< HEAD:common/src/main/java/net/timtaran/interactivemc/physics/physics/body/client/body/renderer/VxRigidBodyRenderer.java
package net.timtaran.interactivemc.physics.physics.body.client.body.renderer;

import net.timtaran.interactivemc.physics.physics.body.type.VxRigidBody;
========
package net.timtaran.interactivemc.physics.core.body.client.renderer;

import net.timtaran.interactivemc.physics.core.body.type.VxRigidBody;
>>>>>>>> velthoric/master:common/src/main/java/net/xmx/velthoric/core/body/client/renderer/VxRigidBodyRenderer.java

/**
 * An abstract base renderer for any body that extends {@link VxRigidBody}.
 * Concrete implementations will handle the specific visual representation (e.g., model, texture)
 * of a particular rigid body type.
 *
 * @param <T> The specific type of VxRigidBody this renderer can draw.
 * @author xI-Mx-Ix
 */
public abstract class VxRigidBodyRenderer<T extends VxRigidBody> extends VxBodyRenderer<T> {
}