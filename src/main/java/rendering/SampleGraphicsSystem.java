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
package rendering;

import org.terasology.context.Context;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.In;
import org.terasology.rendering.GraphicEffectsSystem;
import org.terasology.rendering.dag.Node;
import org.terasology.rendering.dag.NodeFactory;
import org.terasology.rendering.dag.RenderGraph;
import org.terasology.rendering.dag.nodes.AmbientOcclusionPassesNode;
import org.terasology.rendering.dag.nodes.BackdropNode;
import org.terasology.rendering.dag.nodes.BloomPassesNode;
import org.terasology.rendering.dag.nodes.BlurPassesNode;
import org.terasology.rendering.dag.nodes.ChunksAlphaRejectNode;
import org.terasology.rendering.dag.nodes.ChunksOpaqueNode;
import org.terasology.rendering.dag.nodes.ChunksRefractiveReflectiveNode;
import org.terasology.rendering.dag.nodes.DirectionalLightsNode;
import org.terasology.rendering.dag.nodes.DownSampleSceneAndUpdateExposureNode;
import org.terasology.rendering.dag.nodes.FirstPersonViewNode;
import org.terasology.rendering.dag.nodes.InitialPostProcessingNode;
import org.terasology.rendering.dag.nodes.LightGeometryNode;
import org.terasology.rendering.dag.nodes.LightShaftsNode;
import org.terasology.rendering.dag.nodes.ObjectsOpaqueNode;
import org.terasology.rendering.dag.nodes.OutlineNode;
import org.terasology.rendering.dag.nodes.OverlaysNode;
import org.terasology.rendering.dag.nodes.PrePostCompositeNode;
import org.terasology.rendering.dag.nodes.ShadowMapNode;
import org.terasology.rendering.dag.nodes.SimpleBlendMaterialsNode;
import org.terasology.rendering.dag.nodes.SkyBandsNode;
import org.terasology.rendering.dag.nodes.ToneMappingNode;
import org.terasology.rendering.dag.nodes.WorldReflectionNode;
import rendering.nodes.FinalNode;
import rendering.nodes.SampleNode;

/**
 * TODO: Add javadocs
 */
@RegisterSystem
public class SampleGraphicsSystem extends BaseComponentSystem {

    @In
    private GraphicEffectsSystem graphicEffectsSystem;

    @In
    private Context context;

    private RenderGraph renderGraph;

    @Override
    public void initialise() {
        super.initialise();

        graphicEffectsSystem.clear(); // TODO: a module should not clear default list of nodes

        // TODO: I think it's good to give ability to module developers to define the whole list.
        // TODO: However there must be a way to just insert a single node.
        NodeFactory nodeFactory = new NodeFactory(context);
        ShadowMapNode shadowMapNode = nodeFactory.createInstance(ShadowMapNode.class);
        Node worldReflectionNode = nodeFactory.createInstance(WorldReflectionNode.class);
        Node backdropNode = nodeFactory.createInstance(BackdropNode.class);
        Node skybandsNode = nodeFactory.createInstance(SkyBandsNode.class);
        Node objectOpaqueNode = nodeFactory.createInstance(ObjectsOpaqueNode.class);
        Node chunksOpaqueNode = nodeFactory.createInstance(ChunksOpaqueNode.class);
        Node chunksAlphaRejectNode = nodeFactory.createInstance(ChunksAlphaRejectNode.class);
        Node overlaysNode = nodeFactory.createInstance(OverlaysNode.class);
        Node firstPersonViewNode = nodeFactory.createInstance(FirstPersonViewNode.class);
        Node lightGeometryNode = nodeFactory.createInstance(LightGeometryNode.class);
        Node directionalLightsNode = nodeFactory.createInstance(DirectionalLightsNode.class);
        Node chunksRefractiveReflectiveNode = nodeFactory.createInstance(ChunksRefractiveReflectiveNode.class);
        Node outlineNode = nodeFactory.createInstance(OutlineNode.class);
        Node ambientOcclusionPassesNode = nodeFactory.createInstance(AmbientOcclusionPassesNode.class);
        Node prePostCompositeNode = nodeFactory.createInstance(PrePostCompositeNode.class);
        Node simpleBlendMaterialsNode = nodeFactory.createInstance(SimpleBlendMaterialsNode.class);
        Node lightShaftsNode = nodeFactory.createInstance(LightShaftsNode.class);
        Node initialPostProcessingNode = nodeFactory.createInstance(InitialPostProcessingNode.class);
        Node downSampleSceneAndUpdateExposure = nodeFactory.createInstance(DownSampleSceneAndUpdateExposureNode.class);
        Node toneMappingNode = nodeFactory.createInstance(ToneMappingNode.class);
        Node bloomPassesNode = nodeFactory.createInstance(BloomPassesNode.class);
        Node blurPassesNode = nodeFactory.createInstance(BlurPassesNode.class);

        // These are our module's custom nodes.
        Node sampleNode = nodeFactory.createInstance(SampleNode.class);
        Node finalNode = nodeFactory.createInstance(FinalNode.class);

        renderGraph = new RenderGraph();
        renderGraph.addNode(shadowMapNode, "shadowMapNode");
        renderGraph.addNode(worldReflectionNode, "worldReflectionNode");
        renderGraph.addNode(backdropNode, "backdropNode");
        renderGraph.addNode(skybandsNode, "skybandsNode");
        renderGraph.addNode(objectOpaqueNode, "objectOpaqueNode");
        renderGraph.addNode(chunksOpaqueNode, "chunksOpaqueNode");
        renderGraph.addNode(chunksAlphaRejectNode, "chunksAlphaRejectNode");
        renderGraph.addNode(overlaysNode, "overlaysNode");
        renderGraph.addNode(firstPersonViewNode, "firstPersonViewNode");
        renderGraph.addNode(lightGeometryNode, "lightGeometryNode");
        renderGraph.addNode(directionalLightsNode, "directionalLightsNode");
        renderGraph.addNode(chunksRefractiveReflectiveNode, "chunksRefractiveReflectiveNode");
        renderGraph.addNode(outlineNode, "outlineNode");
        renderGraph.addNode(ambientOcclusionPassesNode, "ambientOcclusionPassesNode");
        renderGraph.addNode(prePostCompositeNode, "prePostCompositeNode");
        renderGraph.addNode(simpleBlendMaterialsNode, "simpleBlendMaterialsNode");
        renderGraph.addNode(lightShaftsNode, "lightShaftsNode");
        renderGraph.addNode(initialPostProcessingNode, "initialPostProcessingNode");
        renderGraph.addNode(downSampleSceneAndUpdateExposure, "downSampleSceneAndUpdateExposure");
        renderGraph.addNode(toneMappingNode, "toneMappingNode");
        renderGraph.addNode(bloomPassesNode, "bloomPassesNode");
        renderGraph.addNode(blurPassesNode, "blurPassesNode");
        renderGraph.addNode(finalNode, "finalNode ");
        renderGraph.addNode(sampleNode, "sampleNode");


        graphicEffectsSystem.setShadowNode(shadowMapNode);
        graphicEffectsSystem.setRenderGraph(renderGraph);
    }




}
