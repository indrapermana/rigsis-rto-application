import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDerivedUnit } from 'app/shared/model/derived-unit.model';
import { DerivedUnitService } from './derived-unit.service';
import { IBaseUnit } from 'app/shared/model/base-unit.model';
import { BaseUnitService } from 'app/entities/base-unit';

@Component({
    selector: 'jhi-derived-unit-update',
    templateUrl: './derived-unit-update.component.html'
})
export class DerivedUnitUpdateComponent implements OnInit {
    derivedUnit: IDerivedUnit;
    isSaving: boolean;

    baseunits: IBaseUnit[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private derivedUnitService: DerivedUnitService,
        private baseUnitService: BaseUnitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ derivedUnit }) => {
            this.derivedUnit = derivedUnit;
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
        if (this.derivedUnit.id !== undefined) {
            this.subscribeToSaveResponse(this.derivedUnitService.update(this.derivedUnit));
        } else {
            this.subscribeToSaveResponse(this.derivedUnitService.create(this.derivedUnit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDerivedUnit>>) {
        result.subscribe((res: HttpResponse<IDerivedUnit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
