import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    UnitTypeComponent,
    UnitTypeDetailComponent,
    UnitTypeUpdateComponent,
    UnitTypeDeletePopupComponent,
    UnitTypeDeleteDialogComponent,
    unitTypeRoute,
    unitTypePopupRoute
} from './';

const ENTITY_STATES = [...unitTypeRoute, ...unitTypePopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnitTypeComponent,
        UnitTypeDetailComponent,
        UnitTypeUpdateComponent,
        UnitTypeDeleteDialogComponent,
        UnitTypeDeletePopupComponent
    ],
    entryComponents: [UnitTypeComponent, UnitTypeUpdateComponent, UnitTypeDeleteDialogComponent, UnitTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppUnitTypeModule {}
