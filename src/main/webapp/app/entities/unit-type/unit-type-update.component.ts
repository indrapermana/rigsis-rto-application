import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from './unit-type.service';
import { IBaseUnit } from 'app/shared/model/base-unit.model';
import { BaseUnitService } from 'app/entities/base-unit';

@Component({
    selector: 'jhi-unit-type-update',
    templateUrl: './unit-type-update.component.html'
})
export class UnitTypeUpdateComponent implements OnInit {
    unitType: IUnitType;
    isSaving: boolean;

    baseunits: IBaseUnit[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private unitTypeService: UnitTypeService,
        private baseUnitService: BaseUnitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unitType }) => {
            this.unitType = unitType;
        });
        this.baseUnitService.query().subscribe(
            (res: HttpResponse<IBaseUnit[]>) => {
                this.baseunits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unitType.id !== undefined) {
            this.subscribeToSaveResponse(this.unitTypeService.update(this.unitType));
        } else {
            this.subscribeToSaveResponse(this.unitTypeService.create(this.unitType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnitType>>) {
        result.subscribe((res: HttpResponse<IUnitType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBaseUnitById(index: number, item: IBaseUnit) {
        return item.id;
    }
}
