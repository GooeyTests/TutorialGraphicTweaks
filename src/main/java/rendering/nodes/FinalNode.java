/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rendering.nodes;

import org.terasology.rendering.dag.AbstractNode;
import org.terasology.rendering.dag.stateChanges.BindFBO;
import org.terasology.rendering.dag.stateChanges.EnableMaterial;
import static org.terasology.rendering.opengl.DefaultDynamicFBOs.FINAL;
import static org.terasology.rendering.opengl.OpenGLUtils.renderFullScreenQuad;

/**
 * TODO: Add javadocs
 */
public class FinalNode extends AbstractNode {

    @Override
    public void initialise() {
        addDesiredStateChange(new EnableMaterial("engine:prog.post"));
        addDesiredStateChange(new BindFBO(FINAL));
    }

    @Override
    public void process() {
        renderFullScreenQuad();
    }
}
