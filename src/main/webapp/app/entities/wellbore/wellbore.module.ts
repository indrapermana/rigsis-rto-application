import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    WellboreComponent,
    WellboreDetailComponent,
    WellboreUpdateComponent,
    WellboreDeletePopupComponent,
    WellboreDeleteDialogComponent,
    wellboreRoute,
    wellborePopupRoute
} from './';

const ENTITY_STATES = [...wellboreRoute, ...wellborePopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WellboreComponent,
        WellboreDetailComponent,
        WellboreUpdateComponent,
        WellboreDeleteDialogComponent,
        WellboreDeletePopupComponent
    ],
    entryComponents: [WellboreComponent, WellboreUpdateComponent, WellboreDeleteDialogComponent, WellboreDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppWellboreModule {}
