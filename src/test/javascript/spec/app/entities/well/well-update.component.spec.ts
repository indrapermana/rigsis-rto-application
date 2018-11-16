/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { WellUpdateComponent } from 'app/entities/well/well-update.component';
import { WellService } from 'app/entities/well/well.service';
import { Well } from 'app/shared/model/well.model';

describe('Component Tests', () => {
    describe('Well Management Update Component', () => {
        let comp: WellUpdateComponent;
        let fixture: ComponentFixture<WellUpdateComponent>;
        let service: WellService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [WellUpdateComponent]
            })
                .overrideTemplate(WellUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WellUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WellService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Well(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.well = entity;
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
                    const entity = new Well();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.well = entity;
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
