package br.com.pensarcomodev.repository;

import br.com.pensarcomodev.entity.Question;
import io.micronaut.context.BeanContext;
import io.micronaut.data.annotation.Query;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


@MicronautTest
public class QuestionRepositoryTest {

    @Inject
    BeanContext beanContext;

    @Test
    public void testQuery() {
        String query = beanContext.getBeanDefinition(QuestionRepository.class)
                .getRequiredMethod("findById", Long.class)
                .getAnnotationMetadata().stringValue(Query.class)
                .orElse(null);
        System.out.println(query);
    }

    @Test
    public void testFindTagByQuestionIdEquals() {
        String query = beanContext.getBeanDefinition(QuestionTagRelationRepository.class)
                .getRequiredMethod("findTagByQuestionIdEquals", Long.class)
                .getAnnotationMetadata().stringValue(Query.class)
                .orElse(null);
        System.out.println(query);
    }

    @Test
    public void testFindTagByQuestionIdEquals2() {
        String query = beanContext.getBeanDefinition(QuestionTagRelationRepository.class)
                .getRequiredMethod("findTagByQuestionEquals", Question.class)
                .getAnnotationMetadata().stringValue(Query.class)
                .orElse(null);
        System.out.println(query);
    }

    @Test
    public void testFindTagsQuery() {
        String query = beanContext.getBeanDefinition(QuestionTagRelationRepository.class)
                .getRequiredMethod("findTagByQuestionIdEquals", Long.class)
                .getAnnotationMetadata().stringValue(Query.class)
                .orElse(null);
        System.out.println(query);
    }
}
