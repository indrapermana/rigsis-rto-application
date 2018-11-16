import { NgModule } from '@angular/core';

import { RtoAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [RtoAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [RtoAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RtoAppSharedCommonModule {}
