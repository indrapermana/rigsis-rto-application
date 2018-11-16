import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    BaseUnitComponent,
    BaseUnitDetailComponent,
    BaseUnitUpdateComponent,
    BaseUnitDeletePopupComponent,
    BaseUnitDeleteDialogComponent,
    baseUnitRoute,
    baseUnitPopupRoute
} from './';

const ENTITY_STATES = [...baseUnitRoute, ...baseUnitPopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BaseUnitComponent,
        BaseUnitDetailComponent,
        BaseUnitUpdateComponent,
        BaseUnitDeleteDialogComponent,
        BaseUnitDeletePopupComponent
    ],
    entryComponents: [BaseUnitComponent, BaseUnitUpdateComponent, BaseUnitDeleteDialogComponent, BaseUnitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppBaseUnitModule {}
