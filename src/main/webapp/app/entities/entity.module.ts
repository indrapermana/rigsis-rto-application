import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RtoAppWellModule } from './well/well.module';
import { RtoAppWellboreModule } from './wellbore/wellbore.module';
import { RtoAppRigModule } from './rig/rig.module';
import { RtoAppJobModule } from './job/job.module';
import { RtoAppWitsServiceModule } from './wits-service/wits-service.module';
import { RtoAppRecordTypeModule } from './record-type/record-type.module';
import { RtoAppRecordItemModule } from './record-item/record-item.module';
import { RtoAppBaseUnitModule } from './base-unit/base-unit.module';
import { RtoAppDerivedUnitModule } from './derived-unit/derived-unit.module';
import { RtoAppUnitTypeModule } from './unit-type/unit-type.module';
import { RtoAppUnitTypeItemModule } from './unit-type-item/unit-type-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RtoAppWellModule,
        RtoAppWellboreModule,
        RtoAppRigModule,
        RtoAppJobModule,
        RtoAppWitsServiceModule,
        RtoAppRecordTypeModule,
        RtoAppRecordItemModule,
        RtoAppBaseUnitModule,
        RtoAppDerivedUnitModule,
        RtoAppUnitTypeModule,
        RtoAppUnitTypeItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppEntityModule {}
