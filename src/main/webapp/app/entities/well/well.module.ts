import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    WellComponent,
    WellDetailComponent,
    WellUpdateComponent,
    WellDeletePopupComponent,
    WellDeleteDialogComponent,
    wellRoute,
    wellPopupRoute
} from './';

const ENTITY_STATES = [...wellRoute, ...wellPopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [WellComponent, WellDetailComponent, WellUpdateComponent, WellDeleteDialogComponent, WellDeletePopupComponent],
    entryComponents: [WellComponent, WellUpdateComponent, WellDeleteDialogComponent, WellDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppWellModule {}
