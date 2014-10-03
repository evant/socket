package me.tatarka.holdr.intellij.plugin;

import com.android.builder.model.AndroidProject;
import com.google.common.collect.Sets;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.project.ModuleData;
import me.tatarka.holdr.compile.model.HoldrConfig;
import me.tatarka.holdr.model.HoldrCompiler;
import org.gradle.tooling.model.idea.IdeaModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.service.project.AbstractProjectResolverExtension;

import java.util.Set;

/**
 * Created by evan on 9/28/14.
 */
public class HoldrProjectResolver extends AbstractProjectResolverExtension {
    private static final Logger LOGGER = Logger.getInstance(HoldrProjectResolver.class);

    @Override
    public void populateModuleExtraModels(IdeaModule gradleModule, DataNode<ModuleData> ideModule) {
        super.populateModuleExtraModels(gradleModule, ideModule);

        AndroidProject androidProject = resolverCtx.getExtraProject(gradleModule, AndroidProject.class);
        HoldrCompiler holdrCompiler = resolverCtx.getExtraProject(gradleModule, HoldrCompiler.class);

        if (androidProject != null) {
            if (holdrCompiler == null) {
                // Check for legacy config model
                HoldrConfig holdrConfig = resolverCtx.getExtraProject(gradleModule, HoldrConfig.class);
                if (holdrConfig != null) {
                    LOGGER.warn("The holdr intellij plugin will drop support of 1.3.0 in the future, please update to 1.4.0+");
                    holdrCompiler = new LegacyHoldrCompiler(holdrConfig);
                }
            }

            if (holdrCompiler != null) {
                ideModule.createChild(HoldrDataService.HOLDR_CONFIG_KEY, new HoldrData(gradleModule.getName(), holdrCompiler));
            }
        }
    }

    @Override
    @NotNull
    public Set<Class> getExtraProjectModelClasses() {
        return Sets.<Class>newHashSet(AndroidProject.class, HoldrCompiler.class, HoldrConfig.class);
    }
}
