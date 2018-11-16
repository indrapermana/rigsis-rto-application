import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBaseUnit } from 'app/shared/model/base-unit.model';
import { BaseUnitService } from './base-unit.service';

@Component({
    selector: 'jhi-base-unit-update',
    templateUrl: './base-unit-update.component.html'
})
export class BaseUnitUpdateComponent implements OnInit {
    baseUnit: IBaseUnit;
    isSaving: boolean;

    constructor(private baseUnitService: BaseUnitService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ baseUnit }) => {
            this.baseUnit = baseUnit;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.baseUnit.id !== undefined) {
            this.subscribeToSaveResponse(this.baseUnitService.update(this.baseUnit));
        } else {
            this.subscribeToSaveResponse(this.baseUnitService.create(this.baseUnit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBaseUnit>>) {
        result.subscribe((res: HttpResponse<IBaseUnit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
