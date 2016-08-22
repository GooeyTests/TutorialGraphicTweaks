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

import org.terasology.assets.ResourceUrn;
import org.terasology.registry.In;
import org.terasology.rendering.GraphicEffectsSystem;
import org.terasology.rendering.assets.material.Material;
import org.terasology.rendering.dag.AbstractNode;
import org.terasology.rendering.dag.stateChanges.EnableMaterial;
import static org.terasology.rendering.opengl.DefaultDynamicFBOs.FINAL;
import static org.terasology.rendering.opengl.OpenGLUtils.renderFullScreenQuad;
import static org.terasology.rendering.opengl.OpenGLUtils.selectActiveTexture;
import org.terasology.rendering.opengl.fbms.DisplayResolutionDependentFBOs;
import org.terasology.rendering.shader.ShaderParametersBase;

/**
 * TODO: Add javadocs
 */
public class SampleNode extends AbstractNode {
    public static final ResourceUrn INVERT_SHADER = new ResourceUrn("TutorialGraphicTweaks:invert");
    public static final ResourceUrn INVERT_MATERIAL = new ResourceUrn("TutorialGraphicTweaks:prog.invert");


    @In
    private GraphicEffectsSystem graphicEffectsSystem;

    @In
    private DisplayResolutionDependentFBOs displayResolutionDependentFBOs;

    @Override
    public void initialise() {
        graphicEffectsSystem.loadShader(INVERT_SHADER, new InvertShaderParameters());
        addDesiredStateChange(new EnableMaterial(INVERT_MATERIAL.toString()));
    }

    @Override
    public void process() {
        renderFullScreenQuad();
    }

    @Override
    public void dispose() {
        super.dispose();

        // TODO: dispose shader
    }

    class InvertShaderParameters extends ShaderParametersBase {

        @Override
        public void applyParameters(Material program) {
            super.applyParameters(program);
            int textureId = 0;
            selectActiveTexture(textureId);
            displayResolutionDependentFBOs.bindFboColorTexture(FINAL.getName());
            program.setInt("texScene", textureId, true);
        }
    }
}
