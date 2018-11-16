import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRecordItem } from 'app/shared/model/record-item.model';
import { RecordItemService } from './record-item.service';
import { IRecordType } from 'app/shared/model/record-type.model';
import { RecordTypeService } from 'app/entities/record-type';

@Component({
    selector: 'jhi-record-item-update',
    templateUrl: './record-item-update.component.html'
})
export class RecordItemUpdateComponent implements OnInit {
    recordItem: IRecordItem;
    isSaving: boolean;

    recordtypes: IRecordType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private recordItemService: RecordItemService,
        private recordTypeService: RecordTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recordItem }) => {
            this.recordItem = recordItem;
        });
        this.recordTypeService.query().subscribe(
            (res: HttpResponse<IRecordType[]>) => {
                this.recordtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recordItem.id !== undefined) {
            this.subscribeToSaveResponse(this.recordItemService.update(this.recordItem));
        } else {
            this.subscribeToSaveResponse(this.recordItemService.create(this.recordItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecordItem>>) {
        result.subscribe((res: HttpResponse<IRecordItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRecordTypeById(index: number, item: IRecordType) {
        return item.id;
    }
}
