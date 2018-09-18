package com.uowee.router.demo;

import com.missile.router.annotation.Router;

@Router(value = {"main"},
        longParams = {"id", "updateTime"},
        booleanParams = "web",
        transfer = "web=>fromWeb")
public class MainActivity extends DumpExtrasActivity {

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
}
