import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    UnitTypeItemComponent,
    UnitTypeItemDetailComponent,
    UnitTypeItemUpdateComponent,
    UnitTypeItemDeletePopupComponent,
    UnitTypeItemDeleteDialogComponent,
    unitTypeItemRoute,
    unitTypeItemPopupRoute
} from './';

const ENTITY_STATES = [...unitTypeItemRoute, ...unitTypeItemPopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnitTypeItemComponent,
        UnitTypeItemDetailComponent,
        UnitTypeItemUpdateComponent,
        UnitTypeItemDeleteDialogComponent,
        UnitTypeItemDeletePopupComponent
    ],
    entryComponents: [
        UnitTypeItemComponent,
        UnitTypeItemUpdateComponent,
        UnitTypeItemDeleteDialogComponent,
        UnitTypeItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppUnitTypeItemModule {}
