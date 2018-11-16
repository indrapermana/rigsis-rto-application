import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    DerivedUnitComponent,
    DerivedUnitDetailComponent,
    DerivedUnitUpdateComponent,
    DerivedUnitDeletePopupComponent,
    DerivedUnitDeleteDialogComponent,
    derivedUnitRoute,
    derivedUnitPopupRoute
} from './';

const ENTITY_STATES = [...derivedUnitRoute, ...derivedUnitPopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DerivedUnitComponent,
        DerivedUnitDetailComponent,
        DerivedUnitUpdateComponent,
        DerivedUnitDeleteDialogComponent,
        DerivedUnitDeletePopupComponent
    ],
    entryComponents: [DerivedUnitComponent, DerivedUnitUpdateComponent, DerivedUnitDeleteDialogComponent, DerivedUnitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppDerivedUnitModule {}
