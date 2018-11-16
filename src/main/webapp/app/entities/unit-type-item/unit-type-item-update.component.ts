import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUnitTypeItem } from 'app/shared/model/unit-type-item.model';
import { UnitTypeItemService } from './unit-type-item.service';
import { IUnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from 'app/entities/unit-type';

@Component({
    selector: 'jhi-unit-type-item-update',
    templateUrl: './unit-type-item-update.component.html'
})
export class UnitTypeItemUpdateComponent implements OnInit {
    unitTypeItem: IUnitTypeItem;
    isSaving: boolean;

    unittypes: IUnitType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private unitTypeItemService: UnitTypeItemService,
        private unitTypeService: UnitTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unitTypeItem }) => {
            this.unitTypeItem = unitTypeItem;
        });
        this.unitTypeService.query().subscribe(
            (res: HttpResponse<IUnitType[]>) => {
                this.unittypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unitTypeItem.id !== undefined) {
            this.subscribeToSaveResponse(this.unitTypeItemService.update(this.unitTypeItem));
        } else {
            this.subscribeToSaveResponse(this.unitTypeItemService.create(this.unitTypeItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnitTypeItem>>) {
        result.subscribe((res: HttpResponse<IUnitTypeItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnitTypeById(index: number, item: IUnitType) {
        return item.id;
    }
}
