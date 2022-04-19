package week3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

class MyUpdatedNeedyClassTest {
    private MyUsefulDependency mock;
    private MyUpdatedNeedyClass fixture;

    @BeforeEach
    public void setup() {
        mock = mock(MyUsefulDependency.class);
        fixture = new MyUpdatedNeedyClass(mock);
    }

    @Test
    void storeSomeUsefulThings() {
        fixture.storeSomeUsefulThings("Fancy");
        // This appears to work - but we don't learn much. More later.
    }

    @Test
    void getSomeUsefulThings() {
        assertThat(fixture.getSomeUsefulThings(), equalTo("null: 0"));
        // Well, it runs, but again we don't learn much. Add behaviour to the mock to improve this test.
    }
}