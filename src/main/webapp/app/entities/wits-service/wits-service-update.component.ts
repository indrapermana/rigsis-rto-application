import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IWitsService } from 'app/shared/model/wits-service.model';
import { WitsServiceService } from './wits-service.service';

@Component({
    selector: 'jhi-wits-service-update',
    templateUrl: './wits-service-update.component.html'
})
export class WitsServiceUpdateComponent implements OnInit {
    witsService: IWitsService;
    isSaving: boolean;

    constructor(private witsServiceService: WitsServiceService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ witsService }) => {
            this.witsService = witsService;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.witsService.id !== undefined) {
            this.subscribeToSaveResponse(this.witsServiceService.update(this.witsService));
        } else {
            this.subscribeToSaveResponse(this.witsServiceService.create(this.witsService));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWitsService>>) {
        result.subscribe((res: HttpResponse<IWitsService>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
