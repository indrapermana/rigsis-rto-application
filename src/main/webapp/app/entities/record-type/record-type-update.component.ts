import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRecordType } from 'app/shared/model/record-type.model';
import { RecordTypeService } from './record-type.service';

@Component({
    selector: 'jhi-record-type-update',
    templateUrl: './record-type-update.component.html'
})
export class RecordTypeUpdateComponent implements OnInit {
    recordType: IRecordType;
    isSaving: boolean;

    constructor(private recordTypeService: RecordTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recordType }) => {
            this.recordType = recordType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recordType.id !== undefined) {
            this.subscribeToSaveResponse(this.recordTypeService.update(this.recordType));
        } else {
            this.subscribeToSaveResponse(this.recordTypeService.create(this.recordType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecordType>>) {
        result.subscribe((res: HttpResponse<IRecordType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
