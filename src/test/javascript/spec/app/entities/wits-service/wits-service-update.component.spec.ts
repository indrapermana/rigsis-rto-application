/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { WitsServiceUpdateComponent } from 'app/entities/wits-service/wits-service-update.component';
import { WitsServiceService } from 'app/entities/wits-service/wits-service.service';
import { WitsService } from 'app/shared/model/wits-service.model';

describe('Component Tests', () => {
    describe('WitsService Management Update Component', () => {
        let comp: WitsServiceUpdateComponent;
        let fixture: ComponentFixture<WitsServiceUpdateComponent>;
        let service: WitsServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WitsServiceUpdateComponent]
            })
                .overrideTemplate(WitsServiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WitsServiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WitsServiceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WitsService(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.witsService = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WitsService();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.witsService = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
