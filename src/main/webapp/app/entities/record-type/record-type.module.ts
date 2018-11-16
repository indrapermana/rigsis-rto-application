import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RtoAppSharedModule } from 'app/shared';
import {
    RecordTypeComponent,
    RecordTypeDetailComponent,
    RecordTypeUpdateComponent,
    RecordTypeDeletePopupComponent,
    RecordTypeDeleteDialogComponent,
    recordTypeRoute,
    recordTypePopupRoute
} from './';

const ENTITY_STATES = [...recordTypeRoute, ...recordTypePopupRoute];

@NgModule({
    imports: [RtoAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecordTypeComponent,
        RecordTypeDetailComponent,
        RecordTypeUpdateComponent,
        RecordTypeDeleteDialogComponent,
        RecordTypeDeletePopupComponent
    ],
    entryComponents: [RecordTypeComponent, RecordTypeUpdateComponent, RecordTypeDeleteDialogComponent, RecordTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RtoAppRecordTypeModule {}
