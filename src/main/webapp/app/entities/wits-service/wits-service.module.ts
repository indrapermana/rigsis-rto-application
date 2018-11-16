import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    WitsServiceComponent,
    WitsServiceDetailComponent,
    WitsServiceUpdateComponent,
    WitsServiceDeletePopupComponent,
    WitsServiceDeleteDialogComponent,
    witsServiceRoute,
    witsServicePopupRoute
} from './';

const ENTITY_STATES = [...witsServiceRoute, ...witsServicePopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WitsServiceComponent,
        WitsServiceDetailComponent,
        WitsServiceUpdateComponent,
        WitsServiceDeleteDialogComponent,
        WitsServiceDeletePopupComponent
    ],
    entryComponents: [WitsServiceComponent, WitsServiceUpdateComponent, WitsServiceDeleteDialogComponent, WitsServiceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppWitsServiceModule {}
